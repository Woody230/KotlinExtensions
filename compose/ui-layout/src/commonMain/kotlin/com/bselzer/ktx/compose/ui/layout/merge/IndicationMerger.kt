package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.DelegatableNode

class IndicationMerger : ComponentMerger<Indication> {
    override val default: Indication = Default

    companion object {
        @Stable
        val Default = object : IndicationNodeFactory {
            override fun create(interactionSource: InteractionSource): DelegatableNode = Node
            override fun equals(other: Any?): Boolean = other === this
            override fun hashCode(): Int = -1
        }

        @Stable
        private val Node = object : Modifier.Node() {
        }
    }
}