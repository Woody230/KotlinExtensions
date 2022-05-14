package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment

class AlignmentMerger : ComponentMerger<Alignment> {
    override val default: Alignment = Default

    companion object {
        @Stable
        val Default = BiasAlignment(Float.NaN, Float.NaN)
    }
}