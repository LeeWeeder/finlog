package com.leeweeder.finlog.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leeweeder.finlog.R
import com.leeweeder.finlog.domain.model.currency.Currency
import com.leeweeder.finlog.ui.account.components.CurrencyPicker
import com.leeweeder.finlog.ui.components.OutlinedTextField
import com.leeweeder.finlog.ui.components.bottom_sheet.FinLogBottomSheetProvider
import com.leeweeder.finlog.ui.components.bottom_sheet.LocalBottomSheetController
import com.leeweeder.finlog.ui.icons.arrowBack
import com.leeweeder.finlog.ui.icons.chevronForward
import com.leeweeder.finlog.ui.icons.creditCard
import com.leeweeder.finlog.ui.icons.currencyBitcoin
import com.leeweeder.finlog.ui.icons.decimalDecrease
import com.leeweeder.finlog.ui.icons.decimalIncrease
import com.leeweeder.finlog.ui.icons.wallet

@Composable
internal fun ManageAccountScreen(
    accountOperation: AccountOperation,
    viewModel: ManageAccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ManageAccountScreen(
        accountOperation = accountOperation,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ManageAccountScreen(
    accountOperation: AccountOperation,
    uiState: CreateAccountUiState,
    onEvent: (ManageAccountEvent) -> Unit
) {
    val sheetState = LocalBottomSheetController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${accountOperation.label} account")
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
        },
        bottomBar = {
            FlexibleBottomAppBar(
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onEvent(ManageAccountEvent.AddAccount)
                    }
                ) {
                    Text(accountOperation.label)
                }
            }
        },
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = {
                focusManager.clearFocus()
            }
        )
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            if (uiState is CreateAccountUiState.Loading) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                val loaded = uiState as CreateAccountUiState.Loaded<*>

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        FlowRow(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            AccountTypeDisplay.entries.forEach { item ->
                                ToggleButton(
                                    checked = item.accountType == loaded.accountType,
                                    onCheckedChange = {
                                        onEvent(
                                            ManageAccountEvent.ChangeAccountType(item.accountType)
                                        )
                                    },
                                    shapes =
                                        when (item.ordinal) {
                                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                            AccountTypeDisplay.entries.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                        },
                                    modifier = Modifier
                                        .semantics { role = Role.RadioButton }
                                        .weight(1f),
                                ) {
                                    Icon(
                                        item.icon,
                                        contentDescription = item.label,
                                        modifier = Modifier.size(ToggleButtonDefaults.IconSize)
                                    )
                                    Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                                    Text(item.label)
                                }
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = loaded.name,
                            onValueChange = { onEvent(ManageAccountEvent.SetName(it)) },
                            label = "Account name",
                            modifier = Modifier.fillMaxWidth(),
                            placeholderText = "e.g. Cash",
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                            singleLine = true
                        )
                    }

                    item {
                        val currency = loaded.currency

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Card(
                                onClick = {
                                    sheetState.show {
                                        CurrencyPicker(
                                            value = currency,
                                            currencies = loaded.currencies,
                                            onValueChange = {
                                                onEvent(ManageAccountEvent.ChangeCurrency(it))
                                                sheetState.hide()
                                            }
                                        )
                                    }
                                },
                                shape = MaterialTheme.shapes.largeIncreased,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp)
                                        .padding(start = 14.dp, end = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            currency.code,
                                            style = MaterialTheme.typography.titleSmallEmphasized,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                        Text(
                                            currency.name,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    Icon(
                                        chevronForward,
                                        contentDescription = "Change currency",
                                        tint = MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                    )
                                }
                            }

                            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                                Card(
                                    shape = MaterialTheme.shapes.extraExtraLarge,
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                                    modifier = Modifier
                                        .width(IntrinsicSize.Max)
                                        .fillMaxHeight(),
                                    onClick = {
                                        sheetState.show {
                                            DecimalPrecisionInput(
                                                loaded.decimalPlaces,
                                                modifier = Modifier.padding(16.dp)
                                            ) {
                                                onEvent(
                                                    ManageAccountEvent.SetDecimalPrecision(
                                                        it
                                                    )
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            pluralStringResource(
                                                R.plurals.decimal_places,
                                                loaded.decimalPlaces,
                                                loaded.decimalPlaces
                                            ),
                                            modifier = Modifier.align(Alignment.Center),
                                            style = MaterialTheme.typography.labelLargeEmphasized,
                                            color = MaterialTheme.colorScheme.tertiary,
                                            textAlign = TextAlign.Center
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
}

@Composable
private fun DecimalPrecisionInput(
    initialDigits: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    var digits by remember { mutableIntStateOf(initialDigits) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Badge(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ) {
            Text(
                pluralStringResource(R.plurals.decimal_places, digits, digits),
                modifier = Modifier.padding(4.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            @Composable
            fun DecimalPrecisionButton(
                icon: ImageVector,
                contentDescription: String,
                enabled: Boolean,
                onClick: () -> Unit
            ) {
                FilledTonalIconButton(
                    onClick = onClick,
                    enabled = enabled,
                    shapes = IconButtonShapes(
                        shape = IconButtonDefaults.smallRoundShape,
                        pressedShape = IconButtonDefaults.smallSquareShape
                    ),
                    modifier = Modifier.size(IconButtonDefaults.smallContainerSize())
                ) {
                    Icon(
                        icon,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                    )
                }
            }

            DecimalPrecisionButton(
                enabled = digits > 0,
                icon = decimalDecrease,
                contentDescription = "Decrease decimal precision"
            ) {
                digits -= 1
                onValueChange(digits)
            }

            Text(
                "0${if (digits == 0) "" else ".${"0".repeat(digits)}"}",
                maxLines = 1,
                style = MaterialTheme.typography.displaySmall,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxFontSize = MaterialTheme.typography.displaySmall.fontSize
                ),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            DecimalPrecisionButton(
                enabled = digits < 18,
                icon = decimalIncrease,
                contentDescription = "Increase decimal precision"
            ) {
                digits += 1
                onValueChange(digits)
            }
        }
    }
}

@Preview
@Composable
private fun DecimalPrecisionInputPreview() {
    DecimalPrecisionInput(2) { }
}

@Preview
@Composable
private fun MangeAccountScreenPreview() {
    FinLogBottomSheetProvider {
        ManageAccountScreen(
            accountOperation = AccountOperation.Add,
            uiState = CreateAccountUiState.Loaded.Fiat(
                name = "",
                AccountType.Fiat.Debit,
                currencies = listOf(),
                currency = Currency.FiatCurrency("PHP", "Peso", 2),
                decimalPlaces = 18
            )
        ) { }
    }
}


internal sealed interface AccountOperation {
    val label: String

    data object Add : AccountOperation {
        override val label = "Add"
    }

    data class Edit(val accountId: Long) : AccountOperation {
        override val label = "Edit"
    }
}

private enum class AccountTypeDisplay(
    val label: String,
    val icon: ImageVector,
    val accountType: AccountType
) {
    Debit(label = "Debit", icon = wallet, accountType = AccountType.Fiat.Debit),
    Credit(label = "Credit", icon = creditCard, accountType = AccountType.Fiat.Credit),
    Crypto(label = "Crypto", icon = currencyBitcoin, accountType = AccountType.Crypto),
}
