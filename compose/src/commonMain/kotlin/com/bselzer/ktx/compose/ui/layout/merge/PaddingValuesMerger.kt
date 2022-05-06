package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

class PaddingValuesMerger : ComponentMerger<PaddingValues> {
    override val default: PaddingValues = Default

    companion object {
        @Stable
        val Default = PaddingValues(0.dp)
    }
}