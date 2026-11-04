package com.leeweeder.finlog.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val addCard: ImageVector
    get() {
        if (_addCard != null) {
            return _addCard!!
        }
        _addCard =
            ImageVector.Builder(
                name = "add_card",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(4f, 20f)
                        quadTo(3.18f, 20f, 2.59f, 19.41f)
                        reflectiveQuadTo(2f, 18f)
                        verticalLineTo(6f)
                        quadTo(2f, 5.18f, 2.59f, 4.59f)
                        reflectiveQuadTo(4f, 4f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(22f, 5.18f, 22f, 6f)
                        verticalLineToRelative(5f)
                        quadToRelative(0f, 0.42f, -0.29f, 0.71f)
                        reflectiveQuadTo(21f, 12f)
                        horizontalLineTo(4f)
                        verticalLineToRelative(6f)
                        horizontalLineToRelative(9f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(14f, 19f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(13f, 20f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(4f, 8f)
                        horizontalLineTo(20f)
                        verticalLineTo(6f)
                        horizontalLineTo(4f)
                        verticalLineTo(8f)
                        close()
                        moveTo(19f, 19f)
                        horizontalLineTo(17f)
                        quadToRelative(-0.43f, 0f, -0.71f, -0.29f)
                        quadTo(16f, 18.43f, 16f, 18f)
                        reflectiveQuadToRelative(0.29f, -0.71f)
                        reflectiveQuadTo(17f, 17f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(15f)
                        quadToRelative(0f, -0.43f, 0.29f, -0.71f)
                        reflectiveQuadTo(20f, 14f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(21f, 15f)
                        verticalLineToRelative(2f)
                        horizontalLineToRelative(2f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(24f, 18f)
                        reflectiveQuadToRelative(-0.29f, 0.71f)
                        reflectiveQuadTo(23f, 19f)
                        horizontalLineTo(21f)
                        verticalLineToRelative(2f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(20f, 22f)
                        reflectiveQuadTo(19.29f, 21.71f)
                        quadTo(19f, 21.43f, 19f, 21f)
                        verticalLineTo(19f)
                        close()
                        moveTo(4f, 18f)
                        verticalLineTo(17f)
                        quadTo(4f, 14.93f, 4f, 13.46f)
                        reflectiveQuadTo(4f, 12f)
                        verticalLineTo(6f)
                        verticalLineTo(18f)
                        close()
                    }
                }
                .build()
        return _addCard!!
    }

@Suppress("ObjectPropertyName")
private var _addCard: ImageVector? = null
