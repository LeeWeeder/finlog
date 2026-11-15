package com.leeweeder.finlog.ui.account

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

@HiltViewModel(assistedFactory = ManageAccountViewModel.Factory::class)
internal class ManageAccountViewModel @AssistedInject constructor(
    @Assisted private val accountId: Long?,
    currenciesRepository: CurrenciesRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val isNewAccount = accountId == null

    @AssistedFactory
    interface Factory {
        fun create(accountId: Long?): ManageAccountViewModel
    }

    private val currencies: StateFlow<Currencies?> = currenciesRepository.getCurrencies().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _createAccountDraft = MutableStateFlow(CreateAccountDraft())

    init {
        viewModelScope.launch {
            if (accountId != null) {
                val account = accountRepository.getById(accountId) ?: return@launch

                _createAccountDraft.update {
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

    private val _eventChannel = Channel<ManageAccountUiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    val uiState = combine(
        _createAccountDraft,
        currencies
    ) { draft, currencies ->
        if (currencies == null) {
            CreateAccountUiState.Loading
        } else {
            val currencies = currencies.values.associateBy { it.code }.toSortedMap()
            // TODO: Implement setup screen
            // TODO: Default to device currency if exist on our own list, otherwise, USD
            val defaultCurrency = currencies.firstEntry().value

            val currency = draft.currencyCode?.let { draftCurrencyCode ->
                currencies[draftCurrencyCode]
            } ?: defaultCurrency

            CreateAccountUiState.Loaded(
                name = draft.name,
                accountType = draft.accountType,
                currency = currency ?: return@combine CreateAccountUiState.Loading,
                currencies = currencies.values.toList(),
                decimalPlaces = draft.decimalPlaces,
                hasNameTakenError = draft.hasNameTakenError
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CreateAccountUiState.Loading
    )

    fun onEvent(event: ManageAccountEvent) {
        when (event) {
            is ManageAccountEvent.ChangeAccountType -> onChangeAccountType(event.accountType)
            is ManageAccountEvent.SetName -> onSetName(event.newName)
            is ManageAccountEvent.ChangeCurrency -> onChangeCurrency(event.currency)
            is ManageAccountEvent.SetDecimalPrecision -> onSetDecimalPrecision(event.digits)
            ManageAccountEvent.PrimaryActionClick -> onPrimaryAction()
            ManageAccountEvent.Delete -> onDeleteAction()
        }
    }

    private fun onChangeCurrency(currency: Currency) {
        _createAccountDraft.update {
            it.copy(
                currencyCode = currency.code,
                decimalPlaces = if (currency is Currency.FiatCurrency) currency.digits else it.decimalPlaces
            )
        }
    }

    private fun onSetName(newName: String) {
        _createAccountDraft.update {
            it.copy(name = newName, hasNameTakenError = false)
        }
    }

    private fun onChangeAccountType(accountType: AccountType) {
        _createAccountDraft.update {
            it.copy(
                accountType = accountType
            )
        }
    }

    private fun onSetDecimalPrecision(digits: Int) {
        _createAccountDraft.update {
            it.copy(decimalPlaces = digits)
        }
    }

    private fun onPrimaryAction() {
        val state = uiState.value as? CreateAccountUiState.Loaded ?: return

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

                _eventChannel.send(ManageAccountUiEvent.PrimaryActionSuccess)
            } catch (_: AccountException.NameTaken) {
                _createAccountDraft.update {
                    it.copy(hasNameTakenError = true)
                }
            }
        }
    }

    private fun onDeleteAction() {
        if (isNewAccount) return

        viewModelScope.launch {
            accountRepository.delete(accountId!!)
            _eventChannel.send(ManageAccountUiEvent.DeleteActionSuccess)
        }
    }
}

internal sealed interface ManageAccountEvent {
    data class ChangeAccountType(val accountType: AccountType) : ManageAccountEvent
    data class ChangeCurrency(val currency: Currency) : ManageAccountEvent
    data class SetName(val newName: String) : ManageAccountEvent
    data class SetDecimalPrecision(val digits: Int) : ManageAccountEvent
    data object PrimaryActionClick : ManageAccountEvent
    data object Delete : ManageAccountEvent
}

internal sealed interface ManageAccountUiEvent {
    data object PrimaryActionSuccess : ManageAccountUiEvent
    data object DeleteActionSuccess : ManageAccountUiEvent
}

internal data class CreateAccountDraft(
    val name: String = "",
    val accountType: AccountType = AccountType.Asset,
    val currencyCode: String? = null,
    val decimalPlaces: Int = 2,
    val hasNameTakenError: Boolean = false
)

internal sealed interface CreateAccountUiState {
    data class Loaded(
        val name: String,
        val accountType: AccountType,
        val currencies: List<Currency>,
        val currency: Currency,
        val decimalPlaces: Int,
        val hasNameTakenError: Boolean = false
    ) : CreateAccountUiState

    data object Loading : CreateAccountUiState
}
