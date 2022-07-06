package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable

class IntMerger : ComponentMerger<Int> {
    override val default: Int = Default

    companion object {
        @Stable
        const val Default = Int.MIN_VALUE + 1
    }
}