package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class VisualTransformationMerger : ComponentMerger<VisualTransformation> {
    override val default: VisualTransformation = Default

    companion object {
        @Stable
        val Default = VisualTransformation { text -> TransformedText(text, OffsetMapping.Identity) }
    }
}