package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class FloatingActionButtonElevationMerger : ComponentMerger<FloatingActionButtonElevation> {
    override val default: FloatingActionButtonElevation = Default

    companion object {
        @Stable
        val Default = object : FloatingActionButtonElevation {
            override fun elevation(interactionSource: InteractionSource): State<Dp> = mutableStateOf(0.dp)
        }
    }
}