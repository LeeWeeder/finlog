package com.leeweeder.finlog.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

internal class FinLogNavigator(private val backStack: NavBackStack<NavKey>) {
    fun toManageAccount(id: Long?) = backStack.add(Screen.ManageAccount(id))
    fun back() = backStack.removeLastOrNull()
}

internal val LocalFinLogNavigator = staticCompositionLocalOf<FinLogNavigator> {
    error("No FinLogNavigator provided")
}