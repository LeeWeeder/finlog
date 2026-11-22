package com.leeweeder.finlog.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.ui.icons.add
import com.leeweeder.finlog.ui.icons.addCard
import com.leeweeder.finlog.ui.icons.arrowCircleDown
import com.leeweeder.finlog.ui.icons.arrowCircleUp
import com.leeweeder.finlog.ui.icons.close
import com.leeweeder.finlog.ui.icons.swapHorizontalCircle
import com.leeweeder.finlog.ui.transactions.TransactionType
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAccountForm: (id: Long?) -> Unit,
    onNavigateToTransactionForm: (id: Long?, transactionType: TransactionType) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState is HomeUiState.Loading) {
        LoadingIndicator()
        return
    }

    HomeScreen(
        uiState = uiState as HomeUiState.Success,
        onNavigateToAccountForm = onNavigateToAccountForm,
        onNavigateToTransactionForm = onNavigateToTransactionForm
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState.Success,
    onNavigateToAccountForm: (id: Long?) -> Unit,
    onNavigateToTransactionForm: (id: Long?, transactionType: TransactionType) -> Unit
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("finlog", fontFamily = FontFamily.Monospace)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButtonMenu(
                expanded = isFabExpanded,
                button = {
                    ToggleFloatingActionButton(
                        checked = isFabExpanded,
                        onCheckedChange = {
                            isFabExpanded = it
                        }
                    ) {
                        // TODO: Morph icons
                        val imageVector by remember {
                            derivedStateOf {
                                if (checkedProgress >= 0.5f) close else add
                            }
                        }

                        Icon(
                            rememberVectorPainter(imageVector),
                            contentDescription = "Add transaction",
                            modifier = Modifier.animateIcon({ checkedProgress })
                        )
                    }
                }
            ) {
                FloatingButtonActions.entries.forEach { action ->
                    FloatingActionButtonMenuItem(
                        onClick = {
                            when (action) {
                                FloatingButtonActions.Account -> onNavigateToAccountForm(null)
                                FloatingButtonActions.Transfer -> onNavigateToTransactionForm(
                                    null,
                                    TransactionType.Transfer
                                )

                                FloatingButtonActions.Income -> onNavigateToTransactionForm(
                                    null,
                                    TransactionType.Income
                                )

                                FloatingButtonActions.Expense -> onNavigateToTransactionForm(
                                    null,
                                    TransactionType.Expense
                                )
                            }
                        },
                        text = {
                            Text(action.label)
                        },
                        icon = {
                            Icon(
                                action.icon,
                                action.label
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Card(
                    shape = MaterialTheme.shapes.extraLargeIncreased,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Text(
                            "Net balance",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "PHP " + uiState.netTotal.display,
                            style = MaterialTheme.typography.displaySmallEmphasized,
                            textAlign = TextAlign.Right,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    items(uiState.accountsWithBalance) { accountWithBalance ->
                        ElevatedCard(
                            onClick = {
                                onNavigateToAccountForm(accountWithBalance.account.id)
                            },
                            modifier = Modifier.width(160.dp),
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Text(
                                    accountWithBalance.account.name,
                                    style = MaterialTheme.typography.labelMediumEmphasized,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                                Text(
                                    accountWithBalance.account.currencyCode,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    accountWithBalance.balance.display,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private enum class FloatingButtonActions(
    val icon: ImageVector,
    val label: String
) {
    Account(addCard, "Add account"),
    Transfer(swapHorizontalCircle, "Transfer"),
    Income(arrowCircleDown, "Add income"),
    Expense(arrowCircleUp, "Add expense")
}

@Composable
private fun AccountCard(
    name: String,
    code: String,
    balance: Amount,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraExtraLarge
    ) {
        Column(
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                name,
                style = MaterialTheme.typography.titleLargeEmphasized
            )
            Spacer(Modifier.height(32.dp))
            Text(
                code + " " + balance.display,
                style = MaterialTheme.typography.headlineLargeEmphasized
            )
        }
    }
}

@Preview
@Composable
private fun AccountCardPreview() {
    AccountCard(
        name = "Cash",
        code = "PHP",
        balance = Amount(BigDecimal("1000000000.00")),
        onClick = {}
    )
}
