package com.leeweeder.finlog.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.ui.icons.add
import com.leeweeder.finlog.ui.icons.close
import com.leeweeder.finlog.ui.icons.paymentArrowDown
import com.leeweeder.finlog.ui.icons.payments
import com.leeweeder.finlog.ui.icons.sendMoney
import com.leeweeder.finlog.ui.icons.wallet
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
                    Text("Home")
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
                                FloatingButtonActions.Income -> TODO()
                                FloatingButtonActions.Expense -> TODO()
                                FloatingButtonActions.Transfer -> TODO()
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(uiState.accountsWithBalance) { accountWithBalance ->
                ElevatedCard(
                    onClick = {
                        // TODO: Implement click
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            accountWithBalance.account.name,
                            style = MaterialTheme.typography.titleMediumEmphasized
                        )
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                LazyRow() {
                    items(uiState.accountsWithBalance) { accountWithBalance ->
                        Card() {
                            Text(accountWithBalance.account.name)
                            Text(accountWithBalance.account.code)
                            Text(accountWithBalance.balance.display)
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                onNavigateToManageAccount(null)
                            }
                        ) {
                            Text("Add account")
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
    Income(wallet, "Record income"),
    Expense(payments, "Record expense"),
    Transfer(sendMoney, "Record transfer")
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
