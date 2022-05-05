package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

internal class ShapeMerger : ComponentMerger<Shape> {
    override val default: Shape = Default

    companion object {
        @Stable
        val Default = object : Shape {
            override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density) = RectangleShape.createOutline(size, layoutDirection, density)
            override fun toString(): String = RectangleShape.toString()
        }
    }
}