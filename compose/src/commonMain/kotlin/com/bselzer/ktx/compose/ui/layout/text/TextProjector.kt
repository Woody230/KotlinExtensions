package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
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
        val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
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
            onTextLayout = { result ->
                layoutResult.value = result
                interactor.onTextLayout(result)
            },
            style = presenter.textStyle,
            modifier = combinedModifier then pointerInputModifier(interactor, layoutResult.value)
        )
    }

    /**
     * Applies the [pointerInput] modifier if the onClickOffset handler exists, mimicking a [ClickableText].
     */
    private fun pointerInputModifier(interactor: TextInteractor, layoutResult: TextLayoutResult?) = if (interactor.onClickOffset == null) {
        Modifier
    } else {
        Modifier.pointerInput(interactor.onClickOffset) {
            detectTapGestures { position ->
                layoutResult?.let { layoutResult ->
                    interactor.onClickOffset.invoke(layoutResult.getOffsetForPosition(position), layoutResult.layoutInput.text)
                }
            }
        }
    }
}