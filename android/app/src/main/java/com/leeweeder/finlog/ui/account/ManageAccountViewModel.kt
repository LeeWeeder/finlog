package com.leeweeder.finlog.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leeweeder.finlog.domain.model.account.AccountType
import com.leeweeder.finlog.domain.model.currency.Currencies
import com.leeweeder.finlog.domain.model.currency.Currency
import com.leeweeder.finlog.domain.repository.AccountRepository
import com.leeweeder.finlog.domain.repository.CurrenciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ManageAccountViewModel @Inject constructor(
    currenciesRepository: CurrenciesRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val currencies: StateFlow<Currencies?> = currenciesRepository.getCurrencies().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _createAccountDraft = MutableStateFlow(CreateAccountDraft())

    val uiState = combine(
        _createAccountDraft,
        currencies
    ) { draft, currencies ->
        if (currencies == null) {
            CreateAccountUiState.Loading
        } else {
            val currencies = currencies.values
            // TODO: Implement setup screen
            // TODO: Default to device currency if exist on our own list, otherwise, USD
            val defaultCurrency = currencies.firstOrNull()

            CreateAccountUiState.Loaded(
                name = draft.name,
                accountType = draft.accountType,
                currency = draft.currency ?: defaultCurrency
                ?: return@combine CreateAccountUiState.Loading,
                currencies = currencies,
                decimalPlaces = draft.decimalPlaces
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
            ManageAccountEvent.AddAccount -> onAddAccount()
        }
    }

    private fun onChangeCurrency(currency: Currency) {
        _createAccountDraft.update {
            it.copy(
                currency = currency,
                decimalPlaces = if (currency is Currency.FiatCurrency) currency.digits else it.decimalPlaces
            )
        }
    }

    private fun onSetName(newName: String) {
        _createAccountDraft.update {
            it.copy(name = newName)
        }
    }

    private fun onChangeAccountType(accountType: AccountType) {
        _createAccountDraft.update {
            it.copy(
                accountType = accountType,
                currency = null
            )
        }
    }

    private fun onSetDecimalPrecision(digits: Int) {
        _createAccountDraft.update {
            it.copy(decimalPlaces = digits)
        }
    }

    private fun onAddAccount() {
        val state = uiState.value as? CreateAccountUiState.Loaded ?: return

        viewModelScope.launch {
            accountRepository.insert(
                currencyCode = state.currency.code,
                name = state.name,
                accountType = state.accountType,
                isIncludedInNetBalance = true, // TODO: Implement show/hide in balance,
                decimalPlaces = state.decimalPlaces,
                iconKey = "wallet" // TODO: Implement account icon customization
            )
        }
    }
}

internal sealed interface ManageAccountEvent {
    data class ChangeAccountType(val accountType: AccountType) : ManageAccountEvent
    data class ChangeCurrency(val currency: Currency) : ManageAccountEvent
    data class SetName(val newName: String) : ManageAccountEvent
    data class SetDecimalPrecision(val digits: Int) : ManageAccountEvent
    data object AddAccount : ManageAccountEvent
}

internal data class CreateAccountDraft(
    val name: String = "",
    val accountType: AccountType = AccountType.Asset,
    val currency: Currency? = null,
    val decimalPlaces: Int = 2
)

internal sealed interface CreateAccountUiState {
    data class Loaded(
        val name: String,
        val accountType: AccountType,
        val currencies: List<Currency>,
        val currency: Currency,
        val decimalPlaces: Int
    ) : CreateAccountUiState

    data object Loading : CreateAccountUiState
}
