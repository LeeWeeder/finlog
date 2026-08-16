package com.leeweeder.finlog.ui.account

import androidx.compose.ui.graphics.vector.ImageVector
import com.leeweeder.finlog.domain.model.account.AssetSymbol
import com.leeweeder.finlog.ui.icons.creditCard
import com.leeweeder.finlog.ui.icons.currencyBitcoin
import com.leeweeder.finlog.ui.icons.wallet
import java.util.Currency

internal data class CreateAccountUiState(
    val name: String,
    val iconKey: String,
    val isIncludedInNetBalance: Boolean,
    val accountType: AccountType
) {
    val isCrypto: Boolean
        get() = accountType is AccountType.CryptoAccount

    sealed interface AccountType {
        val accountTypeSelectionOption: AccountTypeSelectionOption

        sealed interface FiatAccount
            : AccountType {
            val currency: Currency

            data class Debit(override val currency: Currency) : FiatAccount {
                override val accountTypeSelectionOption: AccountTypeSelectionOption =
                    AccountTypeSelectionOption.Debit
            }

            data class Credit(override val currency: Currency) : FiatAccount {
                override val accountTypeSelectionOption: AccountTypeSelectionOption =
                    AccountTypeSelectionOption.Credit
            }
        }

        data class CryptoAccount(
            val assetSymbol: AssetSymbol = AssetSymbol.EMPTY
        ) : AccountType {
            override val accountTypeSelectionOption: AccountTypeSelectionOption =
                AccountTypeSelectionOption.Crypto
        }
    }
}

internal enum class AccountTypeSelectionOption(
    val icon: ImageVector,
    val label: String
) {
    Debit(icon = wallet, label = "Debit"),
    Credit(icon = creditCard, label = "Credit"),
    Crypto(icon = currencyBitcoin, label = "Crypto")
}
