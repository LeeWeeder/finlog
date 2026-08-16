package com.leeweeder.finlog.ui.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.leeweeder.finlog.domain.model.account.AssetSymbol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class CreateAccountViewModel @Inject constructor(

) : ViewModel() {
    private val defaultCurrencyCode =
        mutableStateOf(Currency.getInstance(Locale.getDefault()))

    private val _uiState = MutableStateFlow(
        CreateAccountUiState(
            name = "",
            iconKey = "wallet",
            isIncludedInNetBalance = true,
            accountType = CreateAccountUiState.AccountType.FiatAccount.Debit(currency = defaultCurrencyCode.value)
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.ChangeAccountType -> onChangeAccountType(event.option)
            is CreateAccountEvent.SetName -> onSetName(event.newName)
            is CreateAccountEvent.SetAssetSymbol -> onSetAssetSymbol(event.newSymbol)
            is CreateAccountEvent.ChangeCurrency -> onChangeCurrency(event.currency)
        }
    }

    private fun onChangeCurrency(currency: Currency) {
        if (uiState.value.isCrypto) return

        _uiState.update { currentState ->
            val account: CreateAccountUiState.AccountType.FiatAccount =
                currentState.accountType as? CreateAccountUiState.AccountType.FiatAccount
                    ?: return@update currentState

            currentState.copy(
                accountType = when (account) {
                    is CreateAccountUiState.AccountType.FiatAccount.Credit -> account.copy(currency = currency)
                    is CreateAccountUiState.AccountType.FiatAccount.Debit -> account.copy(currency = currency)
                }
            )
        }
    }

    private fun onSetAssetSymbol(newSymbol: String) {
        if (!uiState.value.isCrypto) return

        _uiState.update { currentState ->
            currentState.copy(
                accountType = (currentState.accountType as? CreateAccountUiState.AccountType.CryptoAccount)?.copy(
                    assetSymbol = AssetSymbol.of(newSymbol)
                ) ?: throw IllegalStateException("Function is called on a non-crypto account.")
            )
        }
    }

    private fun onSetName(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    private fun onChangeAccountType(option: AccountTypeSelectionOption) {
        _uiState.update { currentState ->
            when (option) {
                AccountTypeSelectionOption.Debit -> {
                    currentState.copy(
                        accountType = CreateAccountUiState.AccountType.FiatAccount.Debit(
                            defaultCurrencyCode.value
                        )
                    )
                }

                AccountTypeSelectionOption.Credit -> {
                    currentState.copy(
                        accountType = CreateAccountUiState.AccountType.FiatAccount.Credit(
                            defaultCurrencyCode.value
                        )
                    )
                }

                AccountTypeSelectionOption.Crypto -> {
                    currentState.copy(
                        accountType = CreateAccountUiState.AccountType.CryptoAccount()
                    )
                }
            }
        }
    }
}

internal sealed interface CreateAccountEvent {
    data class ChangeAccountType(val option: AccountTypeSelectionOption) : CreateAccountEvent
    data class ChangeCurrency(val currency: Currency) : CreateAccountEvent
    data class SetName(val newName: String) : CreateAccountEvent
    data class SetAssetSymbol(val newSymbol: String) : CreateAccountEvent
}