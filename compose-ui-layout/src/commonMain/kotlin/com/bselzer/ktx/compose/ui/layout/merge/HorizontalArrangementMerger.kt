package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class HorizontalArrangementMerger : ComponentMerger<Arrangement.Horizontal> {
    override val default: Arrangement.Horizontal = Default

    companion object {
        @Stable
        val Default = object : Arrangement.Horizontal {
            override fun Density.arrange(totalSize: Int, sizes: IntArray, layoutDirection: LayoutDirection, outPositions: IntArray) {
            }
        }
    }
}