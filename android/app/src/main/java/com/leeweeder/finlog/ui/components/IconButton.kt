package com.leeweeder.finlog.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
        tooltip = {
            PlainTooltip {
                Text(contentDescription)
            }
        },
        state = rememberTooltipState()
    ) {
        IconButton(
            onClick = onClick,
            shapes = IconButtonDefaults.shapes(IconButtonDefaults.mediumSquareShape)
        ) {
            Icon(icon, contentDescription = contentDescription)
        }
    }
}

// TODO: Implement to other icon button types that used in this project. Also for the buttons, use square shape