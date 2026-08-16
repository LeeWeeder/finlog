package com.leeweeder.finlog.feature.currency_picker

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Currency

@Composable
fun CurrencyPicker(
    value: Currency,
    onValueChange: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencies = remember { Currency.getAvailableCurrencies().sortedBy { it.displayName } }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp)
    ) {
        items(currencies) { currency ->
            Card(
                onClick = {
                    onValueChange()
                }
            ) { }
        }
    }
}