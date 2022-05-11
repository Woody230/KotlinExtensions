package com.bselzer.ktx.compose.ui.layout.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class TextFieldProjector(
    override val interactor: TextFieldInteractor,
    override val presenter: TextFieldPresenter = TextFieldPresenter.Default
) : Projector<TextFieldInteractor, TextFieldPresenter>() {
    private val labelProjector = interactor.label?.let { label -> TextProjector(label, presenter.label) }
    private val placeholderProjector = interactor.placeholder?.let { placeholder -> TextProjector(placeholder, TextPresenter(textStyle = presenter.textStyle)) }
    private val leadingIconProjector = interactor.leadingIcon?.let { icon -> IconProjector(icon, presenter.icon) }
    private val trailingIconProjector = interactor.trailingIcon?.let { icon -> IconProjector(icon, presenter.icon) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier ->
        TextField(
            value = interactor.value,
            onValueChange = interactor.onValueChange,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            readOnly = interactor.readOnly,
            textStyle = textStyle,
            label = labelProjector?.let { label -> { label.Projection() } },
            placeholder = placeholderProjector?.let { placeholder -> { placeholder.Projection() } },
            leadingIcon = leadingIconProjector?.let { icon -> { icon.Projection() } },
            trailingIcon = trailingIconProjector?.let { icon -> { icon.Projection() } },
            isError = interactor.isError,
            visualTransformation = visualTransformation,
            keyboardOptions = interactor.keyboardOptions,
            keyboardActions = interactor.keyboardActions,
            singleLine = singleLine.toBoolean(),
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
    }
}