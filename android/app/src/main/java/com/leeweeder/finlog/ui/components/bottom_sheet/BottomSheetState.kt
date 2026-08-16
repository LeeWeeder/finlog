package com.leeweeder.finlog.ui.components.bottom_sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

internal class BottomSheetState {
    var isVisible by mutableStateOf(false)
        private set
    var content by mutableStateOf<@Composable () -> Unit>({})
        private  set

    fun show(content: @Composable () -> Unit) {
        this.content = content
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }
}

internal val LocalBottomSheetState = staticCompositionLocalOf<BottomSheetState> {
    error("No BottomSheetState provided — wrap your root composable with FinLogBottomSheetProvider.")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FinLogBottomSheetProvider(content: @Composable () -> Unit) {
    val sheetState = remember { BottomSheetState() }
    val modalSheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden
    )

    CompositionLocalProvider(LocalBottomSheetState provides sheetState) {
        Box {
            content()

            if (sheetState.isVisible) {
                ModalBottomSheet(
                    onDismissRequest = {
                        sheetState.hide()
                    },
                    sheetState = modalSheetState
                ) {
                    sheetState.content()
                }
            }
        }
    }
}