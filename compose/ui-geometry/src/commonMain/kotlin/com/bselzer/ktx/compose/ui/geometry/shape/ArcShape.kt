package com.bselzer.ktx.compose.ui.geometry.shape

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * Creates an arc from the center of the shape that swings from the top moving clockwise.
 *
 * @param startAngle the start angle of the arc
 * @param endAngle the end angle of the arc
 */
class ArcShape(private val startAngle: Float, private val endAngle: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline = Outline.Generic(
        path = Path().apply {
            moveTo(x = size.width / 2, y = size.height / 2)
            arcTo(
                rect = Rect(left = 0f, top = 0f, right = size.width, bottom = size.height),
                startAngleDegrees = startAngle - 90,
                sweepAngleDegrees = endAngle - startAngle,
                forceMoveTo = false
            )
        }
    )
}