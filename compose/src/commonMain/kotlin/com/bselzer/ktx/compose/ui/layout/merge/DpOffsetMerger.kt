package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

class DpOffsetMerger : ComponentMerger<DpOffset> {
    override val default: DpOffset = Default

    companion object {
        @Stable
        val Default = DpOffset(0.dp, 0.dp)
    }
}