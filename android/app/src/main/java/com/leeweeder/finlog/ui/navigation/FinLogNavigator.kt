package com.leeweeder.finlog.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.leeweeder.finlog.ui.transactions.TransactionType

internal class FinLogNavigator(private val backStack: NavBackStack<NavKey>) {
    fun toAccountForm(id: Long?) = backStack.add(Screen.AccountForm(id))
    fun toTransactionForm(id: Long?, transactionType: TransactionType) =
        backStack.add(Screen.TransactionForm(id, transactionType))

    fun back() = backStack.removeLastOrNull()
}

internal val LocalFinLogNavigator = staticCompositionLocalOf<FinLogNavigator> {
    error("No FinLogNavigator provided")
}