package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.material.SwitchColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class SwitchColorsMerger : ComponentMerger<SwitchColors> {
    override val default: SwitchColors = Default

    companion object {
        @Stable
        val Default = object : SwitchColors {
            @Composable
            override fun thumbColor(enabled: Boolean, checked: Boolean): State<Color> = mutableStateOf(Color.Transparent)

            @Composable
            override fun trackColor(enabled: Boolean, checked: Boolean): State<Color> = mutableStateOf(Color.Transparent)
        }
    }
}