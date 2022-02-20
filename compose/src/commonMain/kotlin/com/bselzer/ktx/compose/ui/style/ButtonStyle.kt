package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

/**
 * CompositionLocal containing the preferred ButtonStyle that will be used by Button components by default.
 */
val LocalButtonStyle: ProvidableCompositionLocal<ButtonStyle> = compositionLocalOf { styleNotInitialized() }

/**
 * A wrapper around the standard [Button] composable.
 *
 * @param onClick Will be called when the user clicks the button
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this Button. You can create and pass in your own remembered [MutableInteractionSource] if you want to observe [Interaction]s and customize the appearance / behavior of this Button in different [Interaction]s.
 * @param style the style describing how to lay out the button
 * @param content the content to lay out inside the button
 */
@Composable
fun Button(
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    style: ButtonStyle = LocalButtonStyle.current,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = style.modifier,
    enabled = style.enabled,
    interactionSource = interactionSource,
    elevation = style.elevation,
    shape = style.shape,
    border = style.border,
    colors = style.colors,
    contentPadding = style.contentPadding,
    content = content
)

/**
 * Creates a localized [ButtonStyle].
 */
@Composable
fun buttonStyle(): ButtonStyle = ButtonStyle(
    elevation = ButtonDefaults.elevation(),
    shape = MaterialTheme.shapes.small,
    colors = ButtonDefaults.buttonColors(),
    contentPadding = ButtonDefaults.ContentPadding
)

/**
 * @return the default style for a [TextButton].
 */
@Composable
fun textButtonStyle(): ButtonStyle = buttonStyle().copy(
    colors = ButtonDefaults.textButtonColors(),
    contentPadding = ButtonDefaults.TextButtonContentPadding
)

/**
 * @return the default style for an [OutlinedButton].
 */
@Composable
fun outlinedButtonStyle(): ButtonStyle = buttonStyle().copy(
    border = ButtonDefaults.outlinedBorder,
    colors = ButtonDefaults.outlinedButtonColors(),
)

/**
 * The style arguments associated with a [Button] composable.
 */
data class ButtonStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Controls the enabled state of the button. When false, this button will not be clickable
     */
    val enabled: Boolean = true,

    /**
     * [ButtonElevation] used to resolve the elevation for this button in different states. This controls the size of the shadow below the button. Pass null here to disable elevation for this button. See [ButtonDefaults.elevation].
     */
    val elevation: ButtonElevation,

    /**
     * Defines the button's shape as well as its shadow
     */
    val shape: Shape,

    /**
     * Border to draw around the button
     */
    val border: BorderStroke? = null,

    /**
     * [ButtonColors] that will be used to resolve the background and content color for this button in different states. See [ButtonDefaults.buttonColors].
     */
    val colors: ButtonColors,

    /**
     * The spacing values to apply internally between the container and the content
     */
    val contentPadding: PaddingValues
) : ModifiableStyle