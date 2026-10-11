package com.leeweeder.finlog.ui.account.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leeweeder.finlog.domain.model.currency.Currency

@Composable
internal fun <T : Currency> CurrencyPicker(
    value: T,
    onValueChange: (T) -> Unit,
    currencies: List<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ) {
        items(currencies) { currency ->
            val updateTransition = updateTransition(currency.code == value.code)

            val borderThickness by updateTransition.animateDp { if (it) 2.dp else 0.dp }
            val borderAlpha by updateTransition.animateFloat { if (it) 1f else 0f }

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
                border = BorderStroke(
                    width = borderThickness,
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = borderAlpha)
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    Text(currency.code, style = MaterialTheme.typography.titleSmall)
                    Text(
                        currency.name,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}