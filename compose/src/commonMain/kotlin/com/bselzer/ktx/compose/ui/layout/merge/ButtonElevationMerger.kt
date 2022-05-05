package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal class ButtonElevationMerger : ComponentMerger<ButtonElevation> {
    override val default: ButtonElevation = Default

    companion object {
        @Stable
        val Default = object : ButtonElevation {
            @Composable
            override fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp> = mutableStateOf(0.dp)
        }
    }
}