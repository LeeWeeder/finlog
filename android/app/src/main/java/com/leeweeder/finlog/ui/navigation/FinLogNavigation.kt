package com.leeweeder.finlog.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.leeweeder.finlog.ui.account.CreateAccountScreen

@Composable
internal fun FinLogNavigation(modifier: Modifier = Modifier) {
    val backstack = rememberNavBackStack(Screen.Home)

    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screen.Home> {
                Scaffold {
                    Column(modifier = Modifier.padding(it)) {
                        Button(onClick = {
                            backstack.add(Screen.CreateAccount)
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