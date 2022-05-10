package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.material.CheckboxColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState

class CheckboxColorsMerger : ComponentMerger<CheckboxColors> {
    override val default: CheckboxColors = Default

    companion object {
        @Stable
        val Default = object : CheckboxColors {
            @Composable
            override fun borderColor(enabled: Boolean, state: ToggleableState): State<Color> = mutableStateOf(Color.Transparent)

            @Composable
            override fun boxColor(enabled: Boolean, state: ToggleableState): State<Color> = mutableStateOf(Color.Transparent)

            @Composable
            override fun checkmarkColor(state: ToggleableState): State<Color> = mutableStateOf(Color.Transparent)
        }
    }
}