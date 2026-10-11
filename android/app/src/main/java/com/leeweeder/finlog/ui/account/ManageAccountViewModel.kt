package com.leeweeder.finlog.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leeweeder.finlog.domain.model.account.FiatAccountType
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
            when (draft.accountType) {
                AccountType.Crypto -> {
                    val currencies = currencies.cryptoCurrencies
                    CreateAccountUiState.Loaded.Crypto(
                        name = draft.name,
                        accountType = draft.accountType,
                        currency = draft.currency ?: currencies.firstOrNull()
                        ?: return@combine CreateAccountUiState.Loading,
                        currencies = currencies,
                        decimalPlaces = draft.decimalPlaces
                    )
                }

                is AccountType.Fiat -> {
                    val currencies = currencies.fiatCurrencies
                    val defaultCurrency = currencies.firstOrNull()

                    val currency = draft.currency ?: defaultCurrency
                    ?: return@combine CreateAccountUiState.Loading

                    CreateAccountUiState.Loaded.Fiat(
                        name = draft.name,
                        accountType = draft.accountType,
                        currency = currency,
                        currencies = currencies,
                        decimalPlaces = draft.decimalPlaces
                    )
                }
            }
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
        val state = uiState.value as? CreateAccountUiState.Loaded<*> ?: return

        viewModelScope.launch {
            when (state.accountType) {
                AccountType.Crypto -> TODO()
                AccountType.Fiat.Credit -> TODO()
                AccountType.Fiat.Debit -> {
                    accountRepository.insertFiatAccount(
                        currencyCode = state.currency.code,
                        name = state.name,
                        fiatAccountType = FiatAccountType.Debit,
                        isIncludedInNetBalance = true, // TODO: Implement show/hide in balance,
                        decimalPlaces = state.decimalPlaces,
                        iconKey = "wallet" // TODO: Implement account icon customization
                    )
                }
            }
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
    val accountType: AccountType = AccountType.Fiat.Debit,
    val currency: Currency? = null,
    val decimalPlaces: Int = 2
)

internal sealed interface CreateAccountUiState {
    sealed interface Loaded<T : Currency> : CreateAccountUiState {
        val name: String
        val accountType: AccountType
        val currencies: List<T>
        val currency: Currency
        val decimalPlaces: Int

        data class Fiat(
            override val name: String,
            override val accountType: AccountType,
            override val currencies: List<Currency.FiatCurrency>,
            override val currency: Currency,
            override val decimalPlaces: Int
        ) : Loaded<Currency.FiatCurrency>

        data class Crypto(
            override val name: String,
            override val accountType: AccountType,
            override val currencies: List<Currency.CryptoCurrency>,
            override val currency: Currency,
            override val decimalPlaces: Int
        ) : Loaded<Currency.CryptoCurrency>
    }

    data object Loading : CreateAccountUiState
}

internal sealed interface AccountType {
    sealed interface Fiat : AccountType {
        data object Credit : Fiat
        data object Debit : Fiat
    }

    data object Crypto : AccountType
}

