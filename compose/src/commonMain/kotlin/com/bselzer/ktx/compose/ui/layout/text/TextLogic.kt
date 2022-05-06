package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class TextLogic(
    val text: AnnotatedString,
    val onTextLayout: (TextLayoutResult) -> Unit = {},
    val inlineContent: Map<String, InlineTextContent> = mapOf()
) : LogicModel {
    constructor(text: String) : this(AnnotatedString(text))
}