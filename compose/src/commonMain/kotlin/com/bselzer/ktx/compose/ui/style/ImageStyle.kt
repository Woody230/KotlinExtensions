package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.style.StyleProvider.Companion.provider
import com.bselzer.ktx.function.objects.nullMerge
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred ImageStyle that will be used by Image components by default.
 */
val LocalImageStyle: StyleProvider<ImageStyle> = compositionLocalOf { ImageStyle.Default }.provider()

/**
 * A wrapper around the standard [Image] composable.
 *
 * @param painter to draw
 * @param contentDescription text used by accessibility services to describe what this image represents. This should always be provided unless this image is used for decorative purposes, and does not represent a meaningful action that a user can take. This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
 * @param style the style describing how to lay out the image
 */
@Composable
fun Image(
    painter: Painter,
    contentDescription: String?,
    style: ImageStyle = LocalImageStyle.current
) = androidx.compose.foundation.Image(
    painter = painter,
    contentDescription = contentDescription,
    modifier = style.modifier,
    alignment = style.alignment,
    contentScale = style.contentScale,
    alpha = style.alpha,
    colorFilter = style.colorFilter
)

/**
 * The style arguments associated with the [Image] composable.
 */
data class ImageStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Alignment parameter used to place the Painter in the given bounds defined by the width and height.
     */
    val alignment: Alignment = Alignment.Center,

    /**
     * Scale parameter used to determine the aspect ratio scaling to be used if the bounds are a different size from the intrinsic size of the Painter
     */
    val contentScale: ContentScale = ContentScale.Fit,

    /**
     * Opacity to be applied to the Painter when it is rendered onscreen the default renders the Painter completely opaque
     */
    val alpha: Float = DefaultAlpha,

    /**
     * ColorFilter to apply for the Painter when it is rendered onscreen
     */
    val colorFilter: ColorFilter? = null
) : ModifierStyle<ImageStyle>() {
    companion object {
        @Stable
        val Default = ImageStyle()
    }

    override fun safeMerge(other: ImageStyle): ImageStyle = ImageStyle(
        modifier = modifier.then(other.modifier),
        alignment = alignment.safeMerge(other.alignment, Alignment.Center),
        contentScale = contentScale.safeMerge(other.contentScale, ContentScale.Fit),
        alpha = alpha.safeMerge(other.alpha, DefaultAlpha),
        colorFilter = colorFilter.nullMerge(other.colorFilter)
    )

    override fun modify(modifier: Modifier): ImageStyle = copy(modifier = modifier)
}