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
import com.leeweeder.finlog.ui.account.ManageAccountScreen
import com.leeweeder.finlog.ui.account.ManageAccountViewModel
import com.leeweeder.finlog.ui.home.HomeScreen

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
                        onNavigateToManageAccount = {
                            navigator.toManageAccount(it)
                        }
                    )
                }

                entry<Screen.ManageAccount> { key ->
                    val viewModel =
                        hiltViewModel<ManageAccountViewModel, ManageAccountViewModel.Factory>(
                            creationCallback = { factory -> factory.create(key.accountId) }
                        )

                    ManageAccountScreen(
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