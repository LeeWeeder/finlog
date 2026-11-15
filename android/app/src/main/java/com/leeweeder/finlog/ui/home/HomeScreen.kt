package com.leeweeder.finlog.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.ui.icons.add
import com.leeweeder.finlog.ui.icons.addCard
import com.leeweeder.finlog.ui.icons.close
import com.leeweeder.finlog.ui.icons.paymentArrowDown
import com.leeweeder.finlog.ui.icons.paymentArrowUp
import com.leeweeder.finlog.ui.icons.sendMoney
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToManageAccount: (id: Long?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState is HomeUiState.Loading) {
        LoadingIndicator()
        return
    }

    HomeScreen(
        uiState as HomeUiState.Success,
        onNavigateToManageAccount = onNavigateToManageAccount
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState.Success,
    onNavigateToManageAccount: (id: Long?) -> Unit
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {

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
                                FloatingButtonActions.Account -> onNavigateToManageAccount(null)
                                FloatingButtonActions.Transfer -> TODO()
                                FloatingButtonActions.Income -> TODO()
                                FloatingButtonActions.Expense -> TODO()
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
        LazyColumn() {
            item {
                Card(
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Net balance", style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "PHP " + uiState.netTotal.display,
                            style = MaterialTheme.typography.displaySmallEmphasized,
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }

            item {
                LazyRow(
                    contentPadding = innerPadding + PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.accountsWithBalance) { accountWithBalance ->
                        ElevatedCard(
                            onClick = {
                                onNavigateToManageAccount(accountWithBalance.account.accountId)
                            },
                            modifier = Modifier.width(120.dp),
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(
                                    accountWithBalance.account.name,
                                    style = MaterialTheme.typography.titleMediumEmphasized,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    accountWithBalance.account.currencyCode + " " + accountWithBalance.balance.display,
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
    Account(addCard, "Account"),
    Transfer(sendMoney, "Transfer"),
    Income(paymentArrowDown, "Income"),
    Expense(paymentArrowUp, "Expense")
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
