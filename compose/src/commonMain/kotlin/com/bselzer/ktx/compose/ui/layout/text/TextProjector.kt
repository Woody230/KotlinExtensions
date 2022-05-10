package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bselzer.ktx.compose.ui.layout.project.Projector

class TextProjector(
    override val interactor: TextInteractor,
    override val presenter: TextPresenter = TextPresenter.Default
) : Projector<TextInteractor, TextPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        textAlign: TextAlign? = null
    ) = contextualize(modifier) { combinedModifier ->
        Text(
            text = interactor.text,
            modifier = combinedModifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign ?: this.textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap.toBoolean(),
            maxLines = maxLines,
            inlineContent = interactor.inlineContent,
            onTextLayout = interactor.onTextLayout,
            style = textStyle
        )
    }
}