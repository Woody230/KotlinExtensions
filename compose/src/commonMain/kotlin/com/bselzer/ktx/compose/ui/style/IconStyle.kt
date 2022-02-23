package com.bselzer.ktx.compose.ui.style

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * CompositionLocal containing the preferred IconStyle that will be used by Icon components by default.
 */
val LocalIconStyle: ProvidableCompositionLocal<IconStyle> = compositionLocalOf { IconStyle.Default }

/**
 * A wrapper around the standard [Icon] composable.
 *
 * @param imageVector [ImageVector] to draw inside this Icon
 * @param contentDescription text used by accessibility services to describe what this icon represents. This should always be provided unless this icon is used for decorative purposes, and does not represent a meaningful action that a user can take. This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
 * @param style the style describing how to lay out the icon
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    style: IconStyle = LocalIconStyle.localized(),
) = Icon(
    imageVector = imageVector,
    contentDescription = contentDescription,
    modifier = style.modifier,
    tint = style.tint
)

/**
 * The style arguments associated with an [Icon] composable.
 */
data class IconStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Tint to be applied to imageVector. If Color.Unspecified is provided, then no tint is applied
     */
    val tint: Color = Color.Unspecified
) : ModifiableStyle<IconStyle> {
    companion object {
        @Stable
        val Default = IconStyle()
    }

    override fun merge(other: IconStyle?): IconStyle = if (other == null) this else IconStyle(
        modifier = modifier.then(other.modifier),
        tint = tint.merge(other.tint)
    )

    @Composable
    override fun localized(): IconStyle = IconStyle(
        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    ).merge(this)
}