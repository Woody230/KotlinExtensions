package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment

class VerticalAlignmentMerger : ComponentMerger<Alignment.Vertical> {
    override val default: Alignment.Vertical = Default

    companion object {
        @Stable
        val Default = Alignment.Vertical { size, space -> Alignment.Top.align(size, space) }
    }
}