package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment

class HorizontalAlignmentMerger : ComponentMerger<Alignment.Horizontal> {
    override val default: Alignment.Horizontal = Default

    companion object {
        @Stable
        val Default = Alignment.Horizontal { size, space, layoutDirection -> Alignment.Start.align(size, space, layoutDirection) }
    }
}