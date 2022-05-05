package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.drawscope.ContentDrawScope

internal class IndicationMerger : ComponentMerger<Indication> {
    override val default: Indication = Default

    companion object {
        @Stable
        val Default = object : Indication {
            @Composable
            override fun rememberUpdatedInstance(interactionSource: InteractionSource) = Instance
        }

        @Stable
        private val Instance = object : IndicationInstance {
            override fun ContentDrawScope.drawIndication() = drawContent()
        }
    }
}