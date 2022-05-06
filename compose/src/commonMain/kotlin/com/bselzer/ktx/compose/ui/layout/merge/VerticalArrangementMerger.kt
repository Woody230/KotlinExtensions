package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density

class VerticalArrangementMerger : ComponentMerger<Arrangement.Vertical> {
    override val default: Arrangement.Vertical = Default

    companion object {
        @Stable
        val Default = object : Arrangement.Vertical {
            override fun Density.arrange(totalSize: Int, sizes: IntArray, outPositions: IntArray) {
            }
        }
    }
}