package com.leeweeder.finlog.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.leeweeder.finlog.ui.account.CreateAccountScreen

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
                    Scaffold {
                        val navigator = LocalFinLogNavigator.current
                        Column(modifier = Modifier.padding(it)) {
                            Button(onClick = {
                                navigator.toCreateAccount()
                            }) {
                                Text("Create account")
                            }
                        }
                    }
                }

                entry<Screen.CreateAccount> {
                    CreateAccountScreen()
                }
            }
        )
    }
}