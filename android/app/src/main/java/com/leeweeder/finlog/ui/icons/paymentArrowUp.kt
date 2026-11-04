package com.leeweeder.finlog.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val paymentArrowUp: ImageVector
    get() {
        if (_paymentArrowUp != null) {
            return _paymentArrowUp!!
        }
        _paymentArrowUp =
            ImageVector.Builder(
                name = "payment_arrow_up",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    group(scaleY = -1f, pivotY = 12f) {
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
                            moveTo(12f, 11f)
                            quadTo(10.75f, 11f, 9.88f, 10.13f)
                            reflectiveQuadTo(9f, 8f)
                            reflectiveQuadTo(9.88f, 5.88f)
                            reflectiveQuadTo(12f, 5f)
                            reflectiveQuadToRelative(2.13f, 0.88f)
                            reflectiveQuadTo(15f, 8f)
                            reflectiveQuadToRelative(-0.88f, 2.13f)
                            reflectiveQuadTo(12f, 11f)
                            close()
                            moveTo(5f, 14f)
                            quadTo(4.18f, 14f, 3.59f, 13.41f)
                            reflectiveQuadTo(3f, 12f)
                            verticalLineTo(4f)
                            quadTo(3f, 3.17f, 3.59f, 2.59f)
                            reflectiveQuadTo(5f, 2f)
                            horizontalLineTo(19f)
                            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                            reflectiveQuadTo(21f, 4f)
                            verticalLineToRelative(8f)
                            quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                            reflectiveQuadTo(19f, 14f)
                            horizontalLineTo(5f)
                            close()
                            moveTo(7f, 12f)
                            horizontalLineTo(17f)
                            quadToRelative(0f, -0.83f, 0.59f, -1.41f)
                            reflectiveQuadTo(19f, 10f)
                            verticalLineTo(6f)
                            quadTo(18.18f, 6f, 17.59f, 5.41f)
                            reflectiveQuadTo(17f, 4f)
                            horizontalLineTo(7f)
                            quadTo(7f, 4.82f, 6.41f, 5.41f)
                            reflectiveQuadTo(5f, 6f)
                            verticalLineToRelative(4f)
                            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                            quadTo(7f, 11.18f, 7f, 12f)
                            close()
                            moveTo(5f, 12f)
                            verticalLineTo(4f)
                            verticalLineToRelative(8f)
                            close()
                            moveToRelative(6f, 6.2f)
                            lineTo(10.1f, 17.3f)
                            quadTo(9.83f, 17.02f, 9.4f, 17.02f)
                            reflectiveQuadTo(8.7f, 17.3f)
                            reflectiveQuadTo(8.43f, 18f)
                            reflectiveQuadTo(8.7f, 18.7f)
                            lineToRelative(2.6f, 2.6f)
                            quadToRelative(0.15f, 0.15f, 0.32f, 0.21f)
                            reflectiveQuadTo(12f, 21.58f)
                            reflectiveQuadToRelative(0.38f, -0.06f)
                            reflectiveQuadTo(12.7f, 21.3f)
                            lineToRelative(2.6f, -2.6f)
                            quadTo(15.58f, 18.43f, 15.58f, 18f)
                            reflectiveQuadTo(15.3f, 17.3f)
                            quadTo(15.03f, 17.02f, 14.6f, 17.02f)
                            reflectiveQuadTo(13.9f, 17.3f)
                            lineTo(13f, 18.2f)
                            verticalLineTo(16f)
                            quadToRelative(0f, -0.43f, -0.29f, -0.71f)
                            reflectiveQuadTo(12f, 15f)
                            reflectiveQuadToRelative(-0.71f, 0.29f)
                            reflectiveQuadTo(11f, 16f)
                            verticalLineToRelative(2.2f)
                            close()
                        }
                    }
                }
                .build()
        return _paymentArrowUp!!
    }

@Suppress("ObjectPropertyName")
private var _paymentArrowUp: ImageVector? = null
