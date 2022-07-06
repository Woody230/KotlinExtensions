package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp

class PaddingValuesMerger : ComponentMerger<PaddingValues> {
    override val default: PaddingValues = Default

    companion object {
        @Stable
        val Default = PaddingValues(all = Dp.Unspecified)
    }
}