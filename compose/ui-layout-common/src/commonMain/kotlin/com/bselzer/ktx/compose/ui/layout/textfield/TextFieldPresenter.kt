package com.bselzer.ktx.compose.ui.layout.textfield

import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.bselzer.ktx.compose.ui.layout.icon.IconPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class TextFieldPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val label: TextPresenter = TextPresenter.Default,
    val icon: IconPresenter = IconPresenter.Default,

    /**
     * the style to be applied to the input text. The default textStyle uses the LocalTextStyle defined by the theme
     */
    val textStyle: TextStyle = TextStyle.Default,

    /**
     * Transforms the visual representation of the input value.
     * For example, you can use androidx.compose.ui.text.input.PasswordVisualTransformation to create a password text field.
     * By default no visual transformation is applied
     */
    val visualTransformation: VisualTransformation = ComposeMerger.visualTransformation.default,

    /**
     * When set to true, this text field becomes a single horizontally scrolling text field instead of wrapping onto multiple lines.
     * The keyboard will be informed to not show the return key as the ImeAction.
     * Note that maxLines parameter will be ignored as the maxLines attribute will be automatically set to 1.
     */
    val singleLine: TriState = ComposeMerger.triState.default,

    /**
     * The maximum height in terms of maximum number of visible lines.
     * Should be equal or greater than 1. Note that this parameter will be ignored and instead maxLines will be set to 1 if singleLine is set to true.
     */
    val maxLines: Int = ComposeMerger.int.default,

    /**
     * The shape of the text field's container
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     *  TextFieldColors that will be used to resolve color of the text, content (including label, placeholder, leading and trailing icons, indicator line) and background for this text field in different states.
     *  See TextFieldDefaults.textFieldColors
     */
    val colors: TextFieldColors = ComposeMerger.textFieldColors.default
) : Presenter<TextFieldPresenter>(modifier) {
    companion object {
        @Stable
        val Default = TextFieldPresenter()
    }

    override fun safeMerge(other: TextFieldPresenter) = TextFieldPresenter(
        modifier = modifier.merge(other.modifier),
        label = label.merge(other.label),
        icon = icon.merge(other.icon),
        textStyle = textStyle.merge(other.textStyle),
        visualTransformation = ComposeMerger.visualTransformation.safeMerge(visualTransformation, other.visualTransformation),
        singleLine = ComposeMerger.triState.safeMerge(singleLine, other.singleLine),
        maxLines = ComposeMerger.int.safeMerge(maxLines, other.maxLines),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        colors = ComposeMerger.textFieldColors.safeMerge(colors, other.colors)
    )

    // TODO outlined text field
    @Composable
    override fun localized() = TextFieldPresenter(
        textStyle = LocalTextStyle.current,
        visualTransformation = VisualTransformation.None,
        singleLine = TriState.FALSE,
        maxLines = Int.MAX_VALUE,
        shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
        colors = TextFieldDefaults.textFieldColors()
    ).merge(this)
}