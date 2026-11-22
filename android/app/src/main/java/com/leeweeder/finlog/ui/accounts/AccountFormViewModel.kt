package com.leeweeder.finlog.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leeweeder.finlog.domain.exception.account.AccountException
import com.leeweeder.finlog.domain.model.account.AccountType
import com.leeweeder.finlog.domain.model.currency.Currencies
import com.leeweeder.finlog.domain.model.currency.Currency
import com.leeweeder.finlog.domain.repository.AccountEditFields
import com.leeweeder.finlog.domain.repository.AccountRepository
import com.leeweeder.finlog.domain.repository.CurrenciesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AccountFormViewModel.Factory::class)
internal class AccountFormViewModel @AssistedInject constructor(
    @Assisted private val accountId: Long?,
    currenciesRepository: CurrenciesRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val isNewAccount = accountId == null

    @AssistedFactory
    interface Factory {
        fun create(accountId: Long?): AccountFormViewModel
    }

    private val currencies: StateFlow<Currencies?> = currenciesRepository.getCurrencies().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _accountFormDraft = MutableStateFlow(AccountFormDraft())

    init {
        viewModelScope.launch {
            if (accountId != null) {
                val account = accountRepository.getById(accountId) ?: return@launch

                _accountFormDraft.update {
                    it.copy(
                        name = account.name,
                        accountType = account.type,
                        currencyCode = account.currencyCode,
                        decimalPlaces = account.decimalPlaces
                    )
                }
            }
        }
    }

    private val _eventChannel = Channel<AccountFormUiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    val uiState = combine(
        _accountFormDraft,
        currencies
    ) { draft, currencies ->
        if (currencies == null) {
            AccountFormUiState.Loading
        } else {
            val currencies = currencies.values.associateBy { it.code }.toSortedMap()
            // TODO: Implement setup screen
            // TODO: Default to device currency if exist on our own list, otherwise, USD
            val defaultCurrency = currencies.firstEntry().value

            val currency = draft.currencyCode?.let { draftCurrencyCode ->
                currencies[draftCurrencyCode]
            } ?: defaultCurrency

            AccountFormUiState.Loaded(
                name = draft.name,
                accountType = draft.accountType,
                currency = currency ?: return@combine AccountFormUiState.Loading,
                currencies = currencies.values.toList(),
                decimalPlaces = draft.decimalPlaces,
                hasNameTakenError = draft.hasNameTakenError
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AccountFormUiState.Loading
    )

    fun onEvent(event: AccountFormEvent) {
        when (event) {
            is AccountFormEvent.ChangeAccountFormType -> onChangeAccountType(event.accountType)
            is AccountFormEvent.SetName -> onSetName(event.newName)
            is AccountFormEvent.ChangeCurrency -> onChangeCurrency(event.currency)
            is AccountFormEvent.SetDecimalPrecision -> onSetDecimalPrecision(event.digits)
            AccountFormEvent.PrimaryActionClick -> onPrimaryAction()
            AccountFormEvent.Delete -> onDeleteAction()
        }
    }

    private fun onChangeCurrency(currency: Currency) {
        _accountFormDraft.update {
            it.copy(
                currencyCode = currency.code,
                decimalPlaces = if (currency is Currency.FiatCurrency) currency.digits else it.decimalPlaces
            )
        }
    }

    private fun onSetName(newName: String) {
        _accountFormDraft.update {
            it.copy(name = newName, hasNameTakenError = false)
        }
    }

    private fun onChangeAccountType(accountType: AccountType) {
        _accountFormDraft.update {
            it.copy(
                accountType = accountType
            )
        }
    }

    private fun onSetDecimalPrecision(digits: Int) {
        _accountFormDraft.update {
            it.copy(decimalPlaces = digits)
        }
    }

    private fun onPrimaryAction() {
        val state = uiState.value as? AccountFormUiState.Loaded ?: return

        val fields = AccountEditFields(
            name = state.name,
            currencyCode = state.currency.code,
            accountType = state.accountType,
            isIncludedInNetBalance = true, // TODO: Implement flag
            iconKey = "", // TODO: Implement icon key
            decimalPlaces = state.decimalPlaces
        )

        viewModelScope.launch {
            try {
                if (isNewAccount) {
                    accountRepository.insert(fields)
                } else {
                    accountRepository.update(
                        accountId = accountId!!,
                        fields = fields
                    )
                }

                _eventChannel.send(AccountFormUiEvent.PrimaryActionSuccess)
            } catch (_: AccountException.NameTaken) {
                _accountFormDraft.update {
                    it.copy(hasNameTakenError = true)
                }
            }
        }
    }

    private fun onDeleteAction() {
        if (isNewAccount) return

        viewModelScope.launch {
            accountRepository.delete(accountId!!)
            _eventChannel.send(AccountFormUiEvent.DeleteActionSuccess)
        }
    }
}

internal sealed interface AccountFormEvent {
    data class ChangeAccountFormType(val accountType: AccountType) : AccountFormEvent
    data class ChangeCurrency(val currency: Currency) : AccountFormEvent
    data class SetName(val newName: String) : AccountFormEvent
    data class SetDecimalPrecision(val digits: Int) : AccountFormEvent
    data object PrimaryActionClick : AccountFormEvent
    data object Delete : AccountFormEvent
}

internal sealed interface AccountFormUiEvent {
    data object PrimaryActionSuccess : AccountFormUiEvent
    data object DeleteActionSuccess : AccountFormUiEvent
}

internal data class AccountFormDraft(
    val name: String = "",
    val accountType: AccountType = AccountType.Asset,
    val currencyCode: String? = null,
    val decimalPlaces: Int = 2,
    val hasNameTakenError: Boolean = false
)

internal sealed interface AccountFormUiState {
    data class Loaded(
        val name: String,
        val accountType: AccountType,
        val currencies: List<Currency>,
        val currency: Currency,
        val decimalPlaces: Int,
        val hasNameTakenError: Boolean = false
    ) : AccountFormUiState

    data object Loading : AccountFormUiState
}
