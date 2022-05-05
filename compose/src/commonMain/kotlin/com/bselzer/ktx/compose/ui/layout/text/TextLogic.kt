package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class TextLogic(
    val text: AnnotatedString,
    val onTextLayout: (TextLayoutResult) -> Unit = {}
) : LogicModel {
    constructor(text: String) : this(AnnotatedString(text))
}