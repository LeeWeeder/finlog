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
internal val currencyBitcoin: ImageVector
  get() {
    if (_currencyBitcoin != null) {
      return _currencyBitcoin!!
    }
    _currencyBitcoin =
      ImageVector.Builder(
          name = "currency_bitcoin",
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
            moveTo(9f, 20f)
            verticalLineTo(19f)
            horizontalLineTo(7f)
            quadTo(6.58f, 19f, 6.29f, 18.71f)
            quadTo(6f, 18.43f, 6f, 18f)
            reflectiveQuadTo(6.29f, 17.29f)
            reflectiveQuadTo(7f, 17f)
            horizontalLineTo(8f)
            verticalLineTo(7f)
            horizontalLineTo(7f)
            quadTo(6.58f, 7f, 6.29f, 6.71f)
            quadTo(6f, 6.43f, 6f, 6f)
            reflectiveQuadTo(6.29f, 5.29f)
            reflectiveQuadTo(7f, 5f)
            horizontalLineTo(9f)
            verticalLineTo(4f)
            quadTo(9f, 3.57f, 9.29f, 3.29f)
            quadTo(9.58f, 3f, 10f, 3f)
            reflectiveQuadToRelative(0.71f, 0.29f)
            reflectiveQuadTo(11f, 4f)
            verticalLineTo(5f)
            horizontalLineToRelative(2f)
            verticalLineTo(4f)
            quadTo(13f, 3.57f, 13.29f, 3.29f)
            reflectiveQuadTo(14f, 3f)
            reflectiveQuadToRelative(0.71f, 0.29f)
            reflectiveQuadTo(15f, 4f)
            verticalLineTo(5.13f)
            quadToRelative(1.3f, 0.35f, 2.15f, 1.41f)
            reflectiveQuadTo(18f, 9f)
            quadToRelative(0f, 0.72f, -0.25f, 1.39f)
            reflectiveQuadToRelative(-0.7f, 1.19f)
            quadTo(17.93f, 12.1f, 18.46f, 13f)
            reflectiveQuadTo(19f, 15f)
            quadToRelative(0f, 1.65f, -1.18f, 2.82f)
            reflectiveQuadTo(15f, 19f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.43f, -0.29f, 0.71f)
            reflectiveQuadTo(14f, 21f)
            reflectiveQuadTo(13.29f, 20.71f)
            quadTo(13f, 20.43f, 13f, 20f)
            verticalLineTo(19f)
            horizontalLineTo(11f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.43f, -0.29f, 0.71f)
            reflectiveQuadTo(10f, 21f)
            quadTo(9.58f, 21f, 9.29f, 20.71f)
            quadTo(9f, 20.43f, 9f, 20f)
            close()
            moveToRelative(1f, -9f)
            horizontalLineToRelative(4f)
            quadToRelative(0.83f, 0f, 1.41f, -0.59f)
            reflectiveQuadTo(16f, 9f)
            quadTo(16f, 8.17f, 15.41f, 7.59f)
            reflectiveQuadTo(14f, 7f)
            horizontalLineTo(10f)
            verticalLineToRelative(4f)
            close()
            moveToRelative(0f, 6f)
            horizontalLineToRelative(5f)
            quadToRelative(0.83f, 0f, 1.41f, -0.59f)
            reflectiveQuadTo(17f, 15f)
            reflectiveQuadTo(16.41f, 13.59f)
            reflectiveQuadTo(15f, 13f)
            horizontalLineTo(10f)
            verticalLineToRelative(4f)
            close()
          }
        }
        .build()
    return _currencyBitcoin!!
  }

@Suppress("ObjectPropertyName")
private var _currencyBitcoin: ImageVector? = null
