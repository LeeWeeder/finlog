package com.leeweeder.finlog.ui.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leeweeder.finlog.ui.components.IconButton
import com.leeweeder.finlog.ui.components.OutlinedTextField
import com.leeweeder.finlog.ui.components.SelectionButton
import com.leeweeder.finlog.ui.icons.arrowBack
import com.leeweeder.finlog.ui.icons.arrowCircleDown
import com.leeweeder.finlog.ui.icons.arrowCircleUp
import com.leeweeder.finlog.ui.icons.swapHorizontalCircle

@Composable
internal fun TransactionFormScreen(
    viewModel: TransactionFormViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TransactionFormScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TransactionFormScreen(
    uiState: TransactionFormUiState,
    onEvent: (TransactionFormEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            is TransactionFormUiState.Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = uiState.throwable.message ?: "An unknown error occurred."
                )
            }

            TransactionFormUiState.Loading -> LoadingIndicator()
            is TransactionFormUiState.Success -> {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
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
                                Text("Record ${TRANSACTION_TYPE_LABEL_ICON_MAP[uiState.draft.transactionType]?.first?.lowercase()}")
                            }
                        )
                    }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            SelectionButton(
                                items = TransactionType.entries,
                                selectedItem = {
                                    uiState.draft.transactionType == it
                                },
                                label = { TRANSACTION_TYPE_LABEL_ICON_MAP[it]!!.first },
                                onSelectedChange = {
                                    onEvent(TransactionFormEvent.SetTransactionFormType(it))
                                },
                                icon = { TRANSACTION_TYPE_LABEL_ICON_MAP[it]!!.second }
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = uiState.draft.title,
                                onValueChange = {
                                    onEvent(TransactionFormEvent.SetTitle(it))
                                },
                                label = "Title",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        when (uiState.draft) {
                            is TransactionFormDraft.Standalone -> {
                                item {
                                    OutlinedTextField(
                                        value = uiState.draft.amount.display,
                                        onValueChange = {
                                            val text = it.ifBlank { "0" }

                                            onEvent(TransactionFormEvent.Standalone.SetAmount(text))
                                        },
                                        label = "Amount",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                    )
                                }
                            }

                            is TransactionFormDraft.Transfer -> {
                                item {
                                    OutlinedTextField(
                                        value = uiState.draft.amountSent.display,
                                        onValueChange = {
                                            val text = it.ifBlank { "0" }

                                            onEvent(
                                                TransactionFormEvent.Transfer.SetAmount(
                                                    text,
                                                    TransactionFormEvent.Transfer.Role.Source
                                                )
                                            )
                                        },
                                        label = "Amount sent",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                    )
                                }
                                item {
                                    OutlinedTextField(
                                        value = uiState.draft.amountReceived.display,
                                        onValueChange = {
                                            val text = it.ifBlank { "0" }

                                            onEvent(
                                                TransactionFormEvent.Transfer.SetAmount(
                                                    text,
                                                    TransactionFormEvent.Transfer.Role.Destination
                                                )
                                            )
                                        },
                                        label = "Amount received",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private val TRANSACTION_TYPE_LABEL_ICON_MAP = mapOf(
    TransactionType.Expense to ("Expense" to arrowCircleUp),
    TransactionType.Income to ("Income" to arrowCircleDown),
    TransactionType.Transfer to ("Transfer" to swapHorizontalCircle)
)