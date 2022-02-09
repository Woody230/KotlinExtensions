package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred IconButtonStyle that will be used by IconButton components by default.
 */
val LocalIconButtonStyle: ProvidableCompositionLocal<IconButtonStyle> = compositionLocalOf { IconButtonStyle.Default }

/**
 * A wrapper around the standard [IconButton] composable.
 *
 * @param onClick the lambda to be invoked when this icon is pressed
 * @param style the style describing how to lay out the icon button
 * @param content the content (icon) to be drawn inside the IconButton. This is typically an Icon.
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    style: IconButtonStyle = LocalIconButtonStyle.current,
    content: @Composable () -> Unit
) = IconButton(
    onClick = onClick,
    modifier = style.modifier,
    enabled = style.enabled ?: true,
    interactionSource = style.interactionSource ?: remember { MutableInteractionSource() },
    content = content
)

/**
 * A wrapper around the standard [IconButton] composable that displays an [Icon].
 * @param onClick the lambda to be invoked when this icon is pressed
 * @param style the style describing how to lay out the icon button
 * @param imageVector [ImageVector] to draw inside this Icon
 * @param contentDescription text used by accessibility services to describe what this icon represents. This should always be provided unless this icon is used for decorative purposes, and does not represent a meaningful action that a user can take. This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
 * @param style the style describing how to lay out the icon
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    style: IconButtonStyle = LocalIconButtonStyle.current,
    imageVector: ImageVector,
    contentDescription: String?,
    iconStyle: IconStyle = LocalIconStyle.current
) = IconButton(onClick = onClick, style = style) {
    Icon(imageVector = imageVector, contentDescription = contentDescription, style = iconStyle)
}

/**
 * The style arguments associated with an [IconButton] composable.
 */
data class IconButtonStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Whether or not this IconButton will handle input events and appear enabled for semantics purposes
     */
    val enabled: Boolean? = null,

    /**
     * The [MutableInteractionSource] representing the stream of [Interaction]s for this Button. You can create and pass in your own remembered [MutableInteractionSource] if you want to observe [Interaction]s and customize the appearance / behavior of this IconButton in different [Interaction]s.
     */
    val interactionSource: MutableInteractionSource? = null
) : ModifiableStyle<IconButtonStyle> {
    companion object {
        @Stable
        val Default = IconButtonStyle()
    }

    override fun merge(other: IconButtonStyle?): IconButtonStyle = if (other == null) this else IconButtonStyle(
        modifier = modifier.then(other.modifier),
        enabled = enabled.merge(other.enabled),
        interactionSource = interactionSource.merge(other.interactionSource),
    )
}