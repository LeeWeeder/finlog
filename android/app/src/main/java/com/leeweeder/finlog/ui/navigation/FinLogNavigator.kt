package com.leeweeder.finlog.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

internal class FinLogNavigator(private val backStack: NavBackStack<NavKey>) {
    fun toCreateAccount() = backStack.add(Screen.CreateAccount)
    fun back() = backStack.removeLastOrNull()
}

internal val LocalFinLogNavigator = staticCompositionLocalOf<FinLogNavigator> {
    error("No FinLogNavigator provided")
}