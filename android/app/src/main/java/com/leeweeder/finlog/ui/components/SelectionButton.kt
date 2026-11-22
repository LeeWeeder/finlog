package com.leeweeder.finlog.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun <T> SelectionButton(
    items: List<T>,
    selectedItem: (T) -> Boolean,
    label: (T) -> String,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    icon: ((T) -> ImageVector)? = null
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items.forEachIndexed { index, item ->
            ToggleButton(
                checked = selectedItem(item),
                onCheckedChange = { checked ->
                    if (!checked) return@ToggleButton

                    onSelectedChange(item)
                },
                shapes =
                    when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        items.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                modifier = Modifier
                    .semantics { role = Role.RadioButton }
                    .weight(1f),
                icon = if (icon == null) null else {
                    {
                        Icon(
                            icon(item),
                            contentDescription = label(item)
                        )
                    }
                }
            ) {
                Text(label(item))
            }
        }
    }
}