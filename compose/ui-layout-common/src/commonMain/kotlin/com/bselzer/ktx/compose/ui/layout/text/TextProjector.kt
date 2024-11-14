package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bselzer.ktx.compose.ui.layout.project.Projector

class TextProjector(
    interactor: TextInteractor,
    presenter: TextPresenter = TextPresenter.Default
) : Projector<TextInteractor, TextPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        textAlign: TextAlign? = null
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Text(
            text = interactor.text,
            color = presenter.color,
            fontSize = presenter.fontSize,
            fontStyle = presenter.fontStyle,
            fontWeight = presenter.fontWeight,
            fontFamily = presenter.fontFamily,
            letterSpacing = presenter.letterSpacing,
            textDecoration = presenter.textDecoration,
            textAlign = textAlign ?: presenter.textAlign,
            lineHeight = presenter.lineHeight,
            overflow = presenter.overflow,
            softWrap = presenter.softWrap.toBoolean(),
            maxLines = presenter.maxLines,
            inlineContent = interactor.inlineContent,
            onTextLayout = interactor.onTextLayout,
            style = presenter.textStyle,
            modifier = combinedModifier
        )
    }
}