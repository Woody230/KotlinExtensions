package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation

class LayoutOrientationMerger : ComponentMerger<LayoutOrientation> {
    override val default: LayoutOrientation = Default

    companion object {
        @Stable
        val Default = LayoutOrientation.DEFAULT
    }
}