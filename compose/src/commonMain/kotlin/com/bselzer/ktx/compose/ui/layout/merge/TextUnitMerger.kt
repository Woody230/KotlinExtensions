package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.TextUnit

internal class TextUnitMerger : ComponentMerger<TextUnit> {
    override val default: TextUnit = Default

    companion object {
        @Stable
        val Default = TextUnit.Unspecified
    }
}