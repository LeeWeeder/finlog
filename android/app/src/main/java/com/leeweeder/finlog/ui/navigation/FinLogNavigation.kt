package com.leeweeder.finlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.leeweeder.finlog.ui.accounts.AccountFormScreen
import com.leeweeder.finlog.ui.accounts.AccountFormViewModel
import com.leeweeder.finlog.ui.home.HomeScreen
import com.leeweeder.finlog.ui.transactions.TransactionFormScreen
import com.leeweeder.finlog.ui.transactions.TransactionFormViewModel

@Composable
internal fun FinLogNavigation() {
    val backStack = rememberNavBackStack(Screen.Home)
    val navigator = remember { FinLogNavigator(backStack) }

    CompositionLocalProvider(LocalFinLogNavigator provides navigator) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Screen.Home> {
                    val navigator = LocalFinLogNavigator.current

                    HomeScreen(
                        onNavigateToAccountForm = {
                            navigator.toAccountForm(it)
                        },
                        onNavigateToTransactionForm = { id, transactionType ->
                            navigator.toTransactionForm(id, transactionType)
                        }
                    )
                }

                entry<Screen.AccountForm> { key ->
                    val viewModel =
                        hiltViewModel<AccountFormViewModel, AccountFormViewModel.Factory>(
                            creationCallback = { factory -> factory.create(key.accountId) }
                        )

                    AccountFormScreen(
                        viewModel = viewModel,
                        onNavigateBack = {
                            navigator.back()
                        }
                    )
                }

                entry<Screen.TransactionForm> { key ->
                    val viewModel =
                        hiltViewModel<TransactionFormViewModel, TransactionFormViewModel.Factory>(
                            creationCallback = { factory ->
                                factory.create(
                                    key.transactionId,
                                    key.transactionType
                                )
                            }
                        )

                    TransactionFormScreen(
                        viewModel = viewModel,
                        onNavigateBack = {
                            navigator.back()
                        }
                    )
                }
            }
        )
    }
}