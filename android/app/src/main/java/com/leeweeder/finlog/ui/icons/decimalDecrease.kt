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
val decimalDecrease: ImageVector
  get() {
    if (_decimalDecrease != null) {
      return _decimalDecrease!!
    }
    _decimalDecrease =
      ImageVector.Builder(
          name = "decimal_decrease",
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
            moveTo(15.53f, 19f)
            lineToRelative(0.88f, 0.88f)
            quadToRelative(0.27f, 0.3f, 0.29f, 0.71f)
            reflectiveQuadTo(16.4f, 21.3f)
            reflectiveQuadToRelative(-0.71f, 0.3f)
            quadToRelative(-0.41f, 0f, -0.71f, -0.3f)
            lineTo(12.4f, 18.7f)
            quadTo(12.1f, 18.4f, 12.1f, 18f)
            reflectiveQuadToRelative(0.3f, -0.7f)
            lineToRelative(2.58f, -2.6f)
            quadToRelative(0.3f, -0.3f, 0.71f, -0.3f)
            reflectiveQuadToRelative(0.71f, 0.3f)
            reflectiveQuadToRelative(0.29f, 0.71f)
            reflectiveQuadTo(16.4f, 16.13f)
            lineTo(15.53f, 17f)
            horizontalLineToRelative(5.15f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(21.68f, 18f)
            reflectiveQuadToRelative(-0.29f, 0.71f)
            reflectiveQuadTo(20.68f, 19f)
            horizontalLineTo(15.53f)
            close()
            moveTo(4f, 13f)
            horizontalLineTo(3f)
            quadTo(2.58f, 13f, 2.29f, 12.71f)
            quadTo(2f, 12.43f, 2f, 12f)
            verticalLineTo(11f)
            quadTo(2f, 10.58f, 2.29f, 10.29f)
            reflectiveQuadTo(3f, 10f)
            horizontalLineTo(4f)
            quadToRelative(0.42f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(5f, 11f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.42f, -0.29f, 0.71f)
            reflectiveQuadTo(4f, 13f)
            close()
            moveTo(7.03f, 11.98f)
            quadTo(6f, 10.95f, 6f, 9.5f)
            verticalLineToRelative(-4f)
            quadTo(6f, 4.05f, 7.03f, 3.02f)
            reflectiveQuadTo(9.5f, 2f)
            reflectiveQuadToRelative(2.48f, 1.02f)
            reflectiveQuadTo(13f, 5.5f)
            verticalLineToRelative(4f)
            quadToRelative(0f, 1.45f, -1.02f, 2.47f)
            reflectiveQuadTo(9.5f, 13f)
            reflectiveQuadTo(7.03f, 11.98f)
            close()
            moveToRelative(3.54f, -1.41f)
            quadTo(11f, 10.13f, 11f, 9.5f)
            verticalLineToRelative(-4f)
            quadTo(11f, 4.88f, 10.56f, 4.44f)
            reflectiveQuadTo(9.5f, 4f)
            reflectiveQuadTo(8.44f, 4.44f)
            reflectiveQuadTo(8f, 5.5f)
            verticalLineToRelative(4f)
            quadToRelative(0f, 0.63f, 0.44f, 1.06f)
            reflectiveQuadTo(9.5f, 11f)
            reflectiveQuadToRelative(1.06f, -0.44f)
            close()
          }
        }
        .build()
    return _decimalDecrease!!
  }

@Suppress("ObjectPropertyName")
private var _decimalDecrease: ImageVector? = null

