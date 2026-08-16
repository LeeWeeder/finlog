package com.leeweeder.finlog.ui.account

import android.widget.Space
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leeweeder.finlog.ui.components.CurrencyPicker
import com.leeweeder.finlog.ui.components.LabeledContainer
import com.leeweeder.finlog.ui.components.bottom_sheet.LocalBottomSheetState
import com.leeweeder.finlog.ui.icons.arrowBack
import com.leeweeder.finlog.ui.icons.chevronForward

@Composable
internal fun CreateAccountScreen(
    viewModel: CreateAccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CreateAccountScreen(uiState = uiState, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateAccountScreen(
    uiState: CreateAccountUiState,
    onEvent: (CreateAccountEvent) -> Unit
) {
    val sheetState = LocalBottomSheetState.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add account")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // TODO: Implement click
                        }
                    ) {
                        Icon(
                            imageVector = arrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                LabeledContainer("Account type") {
                    FlowRow(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        AccountTypeSelectionOption.entries.forEach { option ->
                            ToggleButton(
                                checked = option == uiState.accountType.accountTypeSelectionOption,
                                onCheckedChange = {
                                    onEvent(
                                        CreateAccountEvent.ChangeAccountType(
                                            option
                                        )
                                    )
                                },
                                shapes =
                                    when (option.ordinal) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        AccountTypeSelectionOption.entries.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                                modifier = Modifier.semantics { role = Role.RadioButton },
                            ) {
                                Icon(
                                    option.icon,
                                    contentDescription = option.label,
                                    modifier = Modifier.size(ToggleButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                                Text(option.label)
                            }
                        }
                    }
                }
            }

            item {
                AnimatedContent(uiState.isCrypto) { isCrypto ->
                    if (isCrypto) {
                        LabeledContainer("Asset symbol") {
                            OutlinedTextField(
                                value = (uiState.accountType as? CreateAccountUiState.AccountType.CryptoAccount)?.assetSymbol?.value
                                    ?: "",
                                onValueChange = {
                                    onEvent(
                                        CreateAccountEvent.SetAssetSymbol(it)
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text("e.g. BTC")
                                },
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                            )
                        }
                    } else {
                        val currency =
                            (uiState.accountType as? CreateAccountUiState.AccountType.FiatAccount)?.currency
                                ?: return@AnimatedContent

                        LabeledContainer("Currency") {
                            OutlinedCard(
                                onClick = {
                                    sheetState.show {
                                        CurrencyPicker(
                                            currency,
                                            onValueChange = {
                                                onEvent(CreateAccountEvent.ChangeCurrency(it))
                                            }
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            currency.currencyCode,
                                            style = MaterialTheme.typography.labelLargeEmphasized
                                        )
                                        Text(
                                            currency.displayName,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Text(
                                        currency.symbol,
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleMediumEmphasized
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Icon(
                                        chevronForward,
                                        contentDescription = "Change currency",
                                        tint = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                LabeledContainer(
                    if (uiState.isCrypto) "Asset name (Optional)"
                    else "Account name"
                ) {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = {
                            onEvent(
                                CreateAccountEvent.SetName(it)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("e.g. ${if (uiState.isCrypto) "Bitcoin" else "Cash"}")
                        }
                    )
                }
            }
        }
    }
}
