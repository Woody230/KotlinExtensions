package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class BorderStrokeMerger : ComponentMerger<BorderStroke> {
    override val default: BorderStroke = Default

    companion object {
        @Stable
        val Default = BorderStroke(0.dp, Color.Unspecified)
    }
}