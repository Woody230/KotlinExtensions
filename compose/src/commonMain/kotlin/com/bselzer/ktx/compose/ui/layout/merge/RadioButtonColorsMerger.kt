package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.material.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class RadioButtonColorsMerger : ComponentMerger<RadioButtonColors> {
    override val default: RadioButtonColors = Default

    companion object {
        @Stable
        val Default = object : RadioButtonColors {
            @Composable
            override fun radioColor(enabled: TriState, selected: TriState): State<Color> = mutableStateOf(Color.Unspecified)
        }
    }
}