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
val sendMoney: ImageVector
  get() {
    if (_sendMoney != null) {
      return _sendMoney!!
    }
    _sendMoney =
      ImageVector.Builder(
          name = "send_money",
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
            moveTo(2f, 12f)
            quadToRelative(0f, 1.7f, 0.85f, 3.11f)
            reflectiveQuadToRelative(2.28f, 2.16f)
            quadToRelative(0.35f, 0.2f, 0.51f, 0.58f)
            reflectiveQuadTo(5.65f, 18.6f)
            quadTo(5.5f, 19f, 5.13f, 19.18f)
            reflectiveQuadTo(4.4f, 19.15f)
            quadToRelative(-1.95f, -1f, -3.18f, -2.89f)
            reflectiveQuadTo(0f, 12f)
            quadTo(0f, 9.65f, 1.19f, 7.77f)
            reflectiveQuadTo(4.3f, 4.9f)
            quadTo(4.65f, 4.7f, 5.04f, 4.86f)
            quadTo(5.43f, 5.02f, 5.6f, 5.43f)
            quadTo(5.78f, 5.77f, 5.63f, 6.15f)
            reflectiveQuadTo(5.13f, 6.72f)
            quadTo(3.7f, 7.47f, 2.85f, 8.89f)
            reflectiveQuadTo(2f, 12f)
            close()
            moveToRelative(12f, 8f)
            quadTo(10.68f, 20f, 8.34f, 17.66f)
            reflectiveQuadTo(6f, 12f)
            quadTo(6f, 8.67f, 8.34f, 6.34f)
            reflectiveQuadTo(14f, 4f)
            quadToRelative(1.4f, 0f, 2.65f, 0.45f)
            reflectiveQuadTo(18.93f, 5.7f)
            quadToRelative(0.32f, 0.25f, 0.32f, 0.65f)
            reflectiveQuadToRelative(-0.3f, 0.7f)
            quadToRelative(-0.27f, 0.27f, -0.69f, 0.3f)
            quadTo(17.85f, 7.38f, 17.5f, 7.13f)
            quadTo(16.75f, 6.57f, 15.86f, 6.29f)
            reflectiveQuadTo(14f, 6f)
            quadTo(11.5f, 6f, 9.75f, 7.75f)
            reflectiveQuadTo(8f, 12f)
            reflectiveQuadToRelative(1.75f, 4.25f)
            reflectiveQuadTo(14f, 18f)
            quadToRelative(0.98f, 0f, 1.86f, -0.29f)
            quadToRelative(0.89f, -0.29f, 1.64f, -0.84f)
            quadToRelative(0.35f, -0.25f, 0.76f, -0.23f)
            quadToRelative(0.41f, 0.03f, 0.69f, 0.3f)
            quadToRelative(0.3f, 0.3f, 0.3f, 0.7f)
            reflectiveQuadTo(18.93f, 18.3f)
            quadToRelative(-1.02f, 0.8f, -2.27f, 1.25f)
            reflectiveQuadTo(14f, 20f)
            close()
            moveToRelative(6.2f, -7f)
            horizontalLineTo(14f)
            quadToRelative(-0.42f, 0f, -0.71f, -0.29f)
            quadTo(13f, 12.43f, 13f, 12f)
            reflectiveQuadToRelative(0.29f, -0.71f)
            reflectiveQuadTo(14f, 11f)
            horizontalLineToRelative(6.2f)
            lineTo(19.3f, 10.1f)
            quadTo(19.03f, 9.82f, 19.03f, 9.4f)
            quadToRelative(0f, -0.42f, 0.27f, -0.7f)
            reflectiveQuadTo(20f, 8.42f)
            quadToRelative(0.43f, 0f, 0.7f, 0.28f)
            lineToRelative(2.6f, 2.6f)
            quadToRelative(0.3f, 0.3f, 0.3f, 0.7f)
            reflectiveQuadToRelative(-0.3f, 0.7f)
            lineToRelative(-2.6f, 2.6f)
            quadTo(20.43f, 15.58f, 20f, 15.58f)
            reflectiveQuadTo(19.3f, 15.3f)
            quadTo(19.03f, 15.03f, 19.03f, 14.6f)
            reflectiveQuadTo(19.3f, 13.9f)
            lineTo(20.2f, 13f)
            close()
          }
        }
        .build()
    return _sendMoney!!
  }

@Suppress("ObjectPropertyName")
private var _sendMoney: ImageVector? = null
