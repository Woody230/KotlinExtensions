package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.function.objects.nullMerge
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred SurfaceStyle that will be used by Surface components by default.
 */
val LocalSurfaceStyle: ProvidableCompositionLocal<SurfaceStyle> = compositionLocalOf { SurfaceStyle.Default }

/**
 * CompositionLocal containing the preferred ClickableSurfaceStyle that will be used by clickable Surface components by default.
 */
val LocalClickableSurfaceStyle: ProvidableCompositionLocal<ClickableSurfaceStyle> = compositionLocalOf { ClickableSurfaceStyle.Default }

/**
 * A wrapper around the standard [Surface] composable.
 *
 * @param style the style describing how to lay out the Surface
 * @param content the content to lay out inside the Surface
 */
@Composable
fun Surface(
    style: SurfaceStyle = LocalSurfaceStyle.localized(),
    content: @Composable () -> Unit,
) = Surface(
    modifier = style.modifier,
    shape = style.shape,
    color = style.color,
    contentColor = style.contentColor,
    border = style.border,
    elevation = style.elevation,
    content = content
)

/**
 * A wrapper around the standard [Surface] composable.
 *
 * @param style the style describing how to lay out the Surface
 * @param interactionSource the MutableInteractionSource representing the stream of Interactions for this Surface.
 * You can create and pass in your own remembered MutableInteractionSource if you want to observe Interactions and customize the appearance / behavior of this Surface in different Interactions.indication - indication to be shown when Surface is pressed.
 * By default, indication from LocalIndication will be used. Pass null to show no indication, or current value from LocalIndication to show theme default
 * @param content the content to lay out inside the Surface
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Surface(
    onClick: () -> Unit,
    onClickLabel: String? = null,
    style: ClickableSurfaceStyle = LocalClickableSurfaceStyle.localized(),
    interactionSource: MutableInteractionSource,
    content: @Composable () -> Unit,
) = Surface(
    onClick = onClick,
    modifier = style.modifier,
    shape = style.shape,
    color = style.color,
    contentColor = style.contentColor,
    border = style.border,
    elevation = style.elevation,
    interactionSource = interactionSource,
    indication = style.indication,
    enabled = style.enabled,
    onClickLabel = onClickLabel,
    role = style.role,
    content = content
)

/**
 * The style arguments associated with a [Surface] composable.
 */
data class SurfaceStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Defines the Surface's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = DefaultShape,

    /**
     * The background color. Use Color.Transparent to have no color.
     */
    val color: Color = Color.Unspecified,

    /**
     * The preferred content color provided by this Surface to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this Surface.
     */
    val contentColor: Color = Color.Unspecified,

    /**
     * Border to draw on top of the Surface
     */
    val border: BorderStroke? = null,

    /**
     * The z-coordinate at which to place this Surface. This controls the size of the shadow below the Surface.
     */
    val elevation: Dp = 0.dp,
) : ModifiableStyle<SurfaceStyle> {
    companion object {
        @Stable
        val Default = SurfaceStyle()
    }

    override fun merge(other: SurfaceStyle?): SurfaceStyle = if (other == null) this else SurfaceStyle(
        modifier = modifier.then(other.modifier),
        shape = shape.merge(other.shape),
        color = color.merge(other.color),
        contentColor = contentColor.merge(other.contentColor),
        border = border.nullMerge(other.border),
        elevation = elevation.safeMerge(other.elevation, 0.dp),
    )

    @Composable
    override fun localized(): SurfaceStyle = run {
        val backgroundColor = MaterialTheme.colors.surface
        SurfaceStyle(
            color = backgroundColor,
            contentColor = contentColorFor(backgroundColor = backgroundColor)
        ).merge(this)
    }
}

/**
 * The style arguments associated with a [Surface] composable that is clickable.
 */
data class ClickableSurfaceStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Defines the Surface's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = RectangleShape,

    /**
     * The background color. Use Color.Transparent to have no color.
     */
    val color: Color = Color.Unspecified,

    /**
     * The preferred content color provided by this Surface to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this Surface.
     */
    val contentColor: Color = Color.Unspecified,

    /**
     * Border to draw on top of the Surface
     */
    val border: BorderStroke? = null,

    /**
     * The z-coordinate at which to place this Surface. This controls the size of the shadow below the Surface.
     */
    val elevation: Dp = 0.dp,

    /**
     * indication to be shown when surface is pressed.
     * By default, indication from LocalIndication will be used. Pass null to show no indication, or current value from LocalIndication to show theme default
     */
    val indication: Indication? = null,

    /**
     * Controls the enabled state of the Surface. When false, this Surface will not be clickable
     */
    val enabled: Boolean = true,

    /**
     *  The type of user interface element.
     *  Accessibility services might use this to describe the element or do customizations.
     *  For example, if the Surface acts as a button, you should pass the Role.Button
     */
    val role: Role? = null,
) : ModifiableStyle<ClickableSurfaceStyle> {
    companion object {
        @Stable
        val Default = ClickableSurfaceStyle()
    }

    override fun merge(other: ClickableSurfaceStyle?): ClickableSurfaceStyle = if (other == null) this else ClickableSurfaceStyle(
        modifier = modifier.then(other.modifier),
        shape = shape.merge(other.shape),
        color = color.merge(other.color),
        contentColor = contentColor.merge(other.contentColor),
        border = border.nullMerge(other.border),
        elevation = elevation.safeMerge(other.elevation, 0.dp),
        indication = indication.nullMerge(other.indication),
        enabled = enabled.safeMerge(other.enabled, true),
        role = role.nullMerge(other.role)
    )

    @Composable
    override fun localized(): ClickableSurfaceStyle = run {
        val backgroundColor = MaterialTheme.colors.surface
        ClickableSurfaceStyle(
            color = backgroundColor,
            contentColor = contentColorFor(backgroundColor = backgroundColor),
            indication = LocalIndication.current
        ).merge(this)
    }
}