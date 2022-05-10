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
    override val interactor: TextInteractor,
    override val presenter: TextPresenter = TextPresenter.Default
) : Projector<TextInteractor, TextPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        textAlign: TextAlign? = null
    ) = contextualize(modifier) { combinedModifier ->
        val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
        Text(
            text = interactor.text,
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
            onTextLayout = { result ->
                layoutResult.value = result
                interactor.onTextLayout(result)
            },
            style = textStyle,
            modifier = combinedModifier then pointerInputModifier(layoutResult.value)
        )
    }

    /**
     * Applies the [pointerInput] modifier if the onClickOffset handler exists, mimicking a [ClickableText].
     */
    private fun pointerInputModifier(layoutResult: TextLayoutResult?) = if (interactor.onClickOffset == null) {
        Modifier
    } else {
        Modifier.pointerInput(interactor.onClickOffset) {
            detectTapGestures { position ->
                layoutResult?.let { layoutResult ->
                    interactor.onClickOffset.invoke(layoutResult.getOffsetForPosition(position))
                }
            }
        }
    }
}