package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class TextProjection(
    override val logic: TextLogic,
    override val presentation: TextPresentation = TextPresentation.Default
) : Projector<TextLogic, TextPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        Text(
            text = logic.text,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap.toBoolean(),
            maxLines = maxLines,
            inlineContent = logic.inlineContent,
            onTextLayout = logic.onTextLayout,
            style = textStyle
        )
    }
}