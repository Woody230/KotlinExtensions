package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable

class FloatMerger : ComponentMerger<Float> {
    override val default: Float = Default

    companion object {
        @Stable
        val Default = Float.NaN
    }
}