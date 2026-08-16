package com.leeweeder.finlog.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

internal sealed interface Screen : NavKey {
    @Serializable data object Home : Screen

    @Serializable data object CreateAccount : Screen
}