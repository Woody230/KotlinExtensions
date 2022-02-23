package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Badge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * CompositionLocal containing the preferred BadgeStyle that will be used by Badge components by default.
 */
val LocalBadgeStyle: ProvidableCompositionLocal<BadgeStyle> = compositionLocalOf { BadgeStyle.Default }

/**
 * A wrapper around the standard [Badge] composable.
 *
 * @param style the style describing how to lay out the badge
 * @param content the content to lay out inside the badge
 */
@Composable
fun Badge(
    style: BadgeStyle = LocalBadgeStyle.localized(),
    content: (@Composable RowScope.() -> Unit)? = null
) = Badge(
    modifier = style.modifier,
    backgroundColor = style.backgroundColor,
    contentColor = style.contentColor,
    content = content
)

/**
 * A wrapper around the standard [Badge] composable.
 *
 * @param style the style describing how to lay out the badge
 * @param text the text within the badge
 * @param textStyle the style of the text within the badge
 */
@Composable
fun Badge(
    style: BadgeStyle = LocalBadgeStyle.localized(),
    text: String,
    textStyle: WordStyle = LocalWordStyle.localized(),
) = Badge(style = style) {
    Text(text = text, style = textStyle)
}

/**
 * The style arguments associated with a [Badge] composable.
 */
data class BadgeStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The background color for the badge
     */
    val backgroundColor: Color = Color.Unspecified,

    /**
     * The color of label text rendered in the badge
     */
    val contentColor: Color = Color.Unspecified,
) : ModifiableStyle<BadgeStyle> {
    companion object {
        @Stable
        val Default = BadgeStyle()
    }

    override fun merge(other: BadgeStyle?): BadgeStyle = if (other == null) this else BadgeStyle(
        modifier = modifier.then(other.modifier),
        backgroundColor = backgroundColor.merge(other.backgroundColor),
        contentColor = contentColor.merge(other.contentColor)
    )

    @Composable
    override fun localized(): BadgeStyle = run {
        val backgroundColor = MaterialTheme.colors.error
        BadgeStyle(
            backgroundColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor = backgroundColor)
        ).merge(this)
    }
}