package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

class PaddingValuesMerger : ComponentMerger<PaddingValues> {
    override val default: PaddingValues = Default

    companion object {
        // NOTE can't use unspecified dp because there is a check on init
        @Stable
        val Default = PaddingValues(all = 0.dp)
    }
}