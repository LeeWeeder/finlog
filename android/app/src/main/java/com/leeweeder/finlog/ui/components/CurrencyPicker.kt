package com.leeweeder.finlog.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Currency
import java.util.Locale

@Composable
internal fun CurrencyPicker(
    value: Currency,
    onValueChange: (Currency) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp)
) {
    val locale = remember { Locale.getAvailableLocales() }

    val currencies by remember(locale) {
        derivedStateOf {
            locale.mapNotNull {
                it.getCurrency()
            }.distinct()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ) {
        items(currencies) { currency ->
            val updateTransition = updateTransition(currency.currencyCode == value.currencyCode)
            val border by updateTransition.animateDp { if (it) 2.dp else 0.dp }
            val cardColor by updateTransition.animateColor {
                if (it) MaterialTheme.colorScheme.surfaceColorAtElevation(
                    4.dp
                ) else MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
            }
            Card(
                onClick = {
                    onValueChange(currency)
                },
                colors = CardDefaults.cardColors(containerColor = cardColor),
                border = BorderStroke(border, MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    Text(currency.currencyCode, style = MaterialTheme.typography.titleSmall)
                    Text(currency.symbol, style = MaterialTheme.typography.headlineSmall)
                    Text(
                        currency.displayName,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

private fun Locale.getCurrency(): Currency? {
    return try {
        Currency.getInstance(this)
    } catch (_: IllegalArgumentException) {
        null
    }
}