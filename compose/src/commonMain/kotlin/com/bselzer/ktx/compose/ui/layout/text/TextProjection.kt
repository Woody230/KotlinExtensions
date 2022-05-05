package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class TextProjection(
    override val logic: TextLogic,
    override val presentation: TextPresentation = TextPresentation()
) : Projectable<TextLogic, TextPresentation> {
    @Composable
    fun project(modifier: Modifier = Modifier) = Text(
        text = logic.text,
        modifier = modifier,
        color = presentation.color,
        fontSize = presentation.fontSize,
        fontStyle = presentation.fontStyle,
        fontWeight = presentation.fontWeight,
        fontFamily = presentation.fontFamily,
        letterSpacing = presentation.letterSpacing,
        textDecoration = presentation.textDecoration,
        textAlign = presentation.textAlign,
        lineHeight = presentation.lineHeight,
        overflow = presentation.overflow,
        softWrap = presentation.softWrap,
        maxLines = presentation.maxLines,
        onTextLayout = logic.onTextLayout,
        style = LocalTextStyle.current.merge(presentation.textStyle)
    )
}