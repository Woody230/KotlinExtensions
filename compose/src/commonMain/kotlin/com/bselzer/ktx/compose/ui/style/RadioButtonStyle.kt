package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred RadioButtonStyle that will be used by RadioButton components by default.
 */
val LocalRadioButtonStyle: ProvidableCompositionLocal<RadioButtonStyle> = compositionLocalOf { RadioButtonStyle.Default }

/**
 * A wrapper around the standard [RadioButton] composable.
 *
 * @param selected whether the button is selected
 * @param style the style describing how to lay out the button
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this Button. You can create and pass in your own remembered [MutableInteractionSource] if you want to observe [Interaction]s and customize the appearance / behavior of this RadioButton in different [Interaction]s.
 * @param onClick callback to be invoked when the RadioButton is being clicked. If null, then this is passive and relies entirely on a higher-level component to control the state.
 */
@Composable
fun RadioButton(
    selected: Boolean,
    style: RadioButtonStyle = LocalRadioButtonStyle.localized(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
) = RadioButton(
    selected = selected,
    onClick = onClick,
    modifier = style.modifier,
    enabled = style.enabled,
    interactionSource = interactionSource,
    colors = style.colors
)

/**
 * The style arguments associated with a [RadioButton] composable.
 */
data class RadioButtonStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Controls the enabled state of the RadioButton. When false, this button will not be selectable and appears in the disabled ui state
     */
    val enabled: Boolean = true,

    /**
     * [RadioButtonColors] that will be used to resolve the background and content color for this RadioButton in different states. See [RadioButtonDefaults.colors].
     */
    val colors: RadioButtonColors = DefaultRadioButtonColors,
) : ModifiableStyle<RadioButtonStyle> {
    companion object {
        @Stable
        val Default = RadioButtonStyle()
    }

    override fun merge(other: RadioButtonStyle?): RadioButtonStyle = if (other == null) this else RadioButtonStyle(
        modifier = modifier.then(other.modifier),
        enabled = enabled.safeMerge(other.enabled, true),
        colors = colors.merge(other.colors),
    )

    @Composable
    override fun localized(): RadioButtonStyle = RadioButtonStyle(
        colors = RadioButtonDefaults.colors()
    ).merge(this)
}