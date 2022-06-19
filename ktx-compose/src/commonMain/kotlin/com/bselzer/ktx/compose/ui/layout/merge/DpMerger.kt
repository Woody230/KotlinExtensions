package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp

class DpMerger : ComponentMerger<Dp> {
    override val default: Dp = Default

    companion object {
        @Stable
        val Default = Dp.Unspecified
    }
}