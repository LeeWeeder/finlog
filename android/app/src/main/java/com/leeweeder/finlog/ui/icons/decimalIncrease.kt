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
val decimalIncrease: ImageVector
  get() {
    if (_decimalIncrease != null) {
      return _decimalIncrease!!
    }
    _decimalIncrease =
      ImageVector.Builder(
          name = "decimal_increase",
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
            moveTo(18.18f, 19f)
            horizontalLineTo(13f)
            quadToRelative(-0.42f, 0f, -0.71f, -0.29f)
            quadTo(12f, 18.43f, 12f, 18f)
            reflectiveQuadToRelative(0.29f, -0.71f)
            reflectiveQuadTo(13f, 17f)
            horizontalLineToRelative(5.18f)
            lineTo(17.3f, 16.13f)
            quadToRelative(-0.27f, -0.3f, -0.29f, -0.71f)
            reflectiveQuadTo(17.3f, 14.7f)
            reflectiveQuadTo(18f, 14.4f)
            reflectiveQuadToRelative(0.7f, 0.3f)
            lineToRelative(2.6f, 2.6f)
            quadToRelative(0.3f, 0.3f, 0.3f, 0.7f)
            reflectiveQuadToRelative(-0.3f, 0.7f)
            lineToRelative(-2.6f, 2.6f)
            quadTo(18.4f, 21.6f, 18f, 21.6f)
            reflectiveQuadTo(17.3f, 21.3f)
            reflectiveQuadTo(17.01f, 20.59f)
            quadToRelative(0.01f, -0.41f, 0.29f, -0.71f)
            lineTo(18.18f, 19f)
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
            moveToRelative(9f, 0f)
            quadTo(15f, 10.95f, 15f, 9.5f)
            verticalLineToRelative(-4f)
            quadTo(15f, 4.05f, 16.03f, 3.02f)
            reflectiveQuadTo(18.5f, 2f)
            reflectiveQuadToRelative(2.48f, 1.02f)
            reflectiveQuadTo(22f, 5.5f)
            verticalLineToRelative(4f)
            quadToRelative(0f, 1.45f, -1.02f, 2.47f)
            reflectiveQuadTo(18.5f, 13f)
            reflectiveQuadTo(16.03f, 11.98f)
            close()
            moveTo(10.56f, 10.56f)
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
            moveToRelative(9f, 0f)
            quadTo(20f, 10.13f, 20f, 9.5f)
            verticalLineToRelative(-4f)
            quadTo(20f, 4.88f, 19.56f, 4.44f)
            reflectiveQuadTo(18.5f, 4f)
            reflectiveQuadTo(17.44f, 4.44f)
            reflectiveQuadTo(17f, 5.5f)
            verticalLineToRelative(4f)
            quadToRelative(0f, 0.63f, 0.44f, 1.06f)
            reflectiveQuadTo(18.5f, 11f)
            reflectiveQuadToRelative(1.06f, -0.44f)
            close()
          }
        }
        .build()
    return _decimalIncrease!!
  }

@Suppress("ObjectPropertyName")
private var _decimalIncrease: ImageVector? = null
