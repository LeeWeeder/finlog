package com.leeweeder.finlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import com.leeweeder.finlog.ui.components.bottom_sheet.FinLogBottomSheetProvider
import com.leeweeder.finlog.ui.home.HomeScreen
import com.leeweeder.finlog.ui.navigation.FinLogNavigation
import com.leeweeder.finlog.ui.theme.FinlogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinlogTheme {
                FinLogBottomSheetProvider {
                    FinLogNavigation()
                }
            }
        }
    }
}