package com.leeweeder.finlog.ui.navigation

import androidx.navigation3.runtime.NavKey
import com.leeweeder.finlog.ui.transactions.TransactionType
import kotlinx.serialization.Serializable

internal sealed interface Screen : NavKey {
    @Serializable
    data object Home : Screen

    @Serializable
    data class AccountForm(
        val accountId: Long?
    ) : Screen

    @Serializable
    data class TransactionForm(
        val transactionId: Long?,
        val transactionType: TransactionType
    ) : Screen
}