package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.material.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class ButtonColorsMerger : ComponentMerger<ButtonColors> {
    override val default: ButtonColors = Default

    companion object {
        @Stable
        val Default = object : ButtonColors {
            @Composable
            override fun backgroundColor(enabled: Boolean): State<Color> = mutableStateOf(Color.Unspecified)

            @Composable
            override fun contentColor(enabled: Boolean): State<Color> = mutableStateOf(Color.Unspecified)
        }
    }
}