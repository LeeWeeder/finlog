package com.leeweeder.finlog.ui.transactions

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.leeweeder.finlog.ui.components.IconButton
import com.leeweeder.finlog.ui.icons.arrowBack

@Composable
internal fun TransactionFormScreen() {

}

@Composable
private fun TransactionFormScreen(
    uiState: TransactionFormUiState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        icon = arrowBack,
                        contentDescription = "Go back to previous screen"
                    )
                },
                title = {
                    Text()
                }
            )
        }
    ) { }
}