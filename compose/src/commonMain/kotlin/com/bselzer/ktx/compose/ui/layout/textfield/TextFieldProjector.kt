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
    interactor: TextFieldInteractor,
    presenter: TextFieldPresenter = TextFieldPresenter.Default
) : Projector<TextFieldInteractor, TextFieldPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val labelProjector = interactor.label?.let { TextProjector(it, presenter.label) }
        val placeholderProjector = interactor.placeholder?.let { TextProjector(it, TextPresenter(textStyle = presenter.textStyle)) }
        val leadingIconProjector = interactor.leadingIcon?.let { IconProjector(it, presenter.icon) }
        val trailingIconProjector = interactor.trailingIcon?.let { IconProjector(it, presenter.icon) }

        TextField(
            value = interactor.value,
            onValueChange = interactor.onValueChange,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            readOnly = interactor.readOnly,
            textStyle = presenter.textStyle,
            label = labelProjector?.let { label -> { label.Projection() } },
            placeholder = placeholderProjector?.let { placeholder -> { placeholder.Projection() } },
            leadingIcon = leadingIconProjector?.let { icon -> { icon.Projection() } },
            trailingIcon = trailingIconProjector?.let { icon -> { icon.Projection() } },
            isError = interactor.isError,
            visualTransformation = presenter.visualTransformation,
            keyboardOptions = interactor.keyboardOptions,
            keyboardActions = interactor.keyboardActions,
            singleLine = presenter.singleLine.toBoolean(),
            maxLines = presenter.maxLines,
            interactionSource = interactionSource,
            shape = presenter.shape,
            colors = presenter.colors
        )
    }
}