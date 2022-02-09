package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred RadioButtonStyle that will be used by RadioButton components by default.
 */
val LocalRadioButtonStyle: ProvidableCompositionLocal<RadioButtonStyle> = compositionLocalOf(structuralEqualityPolicy()) { RadioButtonStyle.Default }

/**
 * A wrapper around the standard [RadioButton] composable.
 *
 * @param onClick Will be called when the user clicks the button
 * @param style the style describing how to lay out the button
 */
@Composable
fun RadioButton(
    selected: Boolean,
    style: RadioButtonStyle = LocalRadioButtonStyle.current,
    onClick: (() -> Unit)? = null,
) = RadioButton(
    selected = selected,
    onClick = onClick,
    modifier = style.modifier,
    enabled = style.enabled ?: true,
    interactionSource = style.interactionSource ?: remember { MutableInteractionSource() },
    colors = style.colors ?: RadioButtonDefaults.colors(),
)

/**
 * The style arguments associated with a [RadioButton] composable.
 */
data class RadioButtonStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Controls the enabled state of the RadioButton. When false, this button will not be selectable and appears in the disabled ui state
     */
    val enabled: Boolean? = null,

    /**
     * The [MutableInteractionSource] representing the stream of [Interaction]s for this Button. You can create and pass in your own remembered [MutableInteractionSource] if you want to observe [Interaction]s and customize the appearance / behavior of this RadioButton in different [Interaction]s.
     */
    val interactionSource: MutableInteractionSource? = null,

    /**
     * [RadioButtonColors] that will be used to resolve the background and content color for this RadioButton in different states. See [RadioButtonDefaults.colors].
     */
    val colors: RadioButtonColors? = null,
) : ModifiableStyle<RadioButtonStyle> {
    companion object {
        @Stable
        val Default = RadioButtonStyle()
    }

    override fun merge(other: RadioButtonStyle?): RadioButtonStyle = if (other == null) this else RadioButtonStyle(
        modifier = modifier.then(other.modifier),
        enabled = enabled.merge(other.enabled),
        interactionSource = interactionSource.merge(other.interactionSource),
        colors = colors.merge(other.colors),
    )
}