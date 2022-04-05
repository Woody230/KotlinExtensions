package com.bselzer.ktx.compose.ui.description

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.style.*

/**
 * CompositionLocal containing the preferred DescriptionStyle that will be used by Description components by default.
 */
val LocalDescriptionStyle: ProvidableCompositionLocal<DescriptionStyle> = compositionLocalOf { DescriptionStyle.Default }

/**
 * A wrapper around the standard [Description] composable.
 *
 * @param style the style describing how to lay out the Description
 */
@Composable
fun Description(
    style: DescriptionStyle = LocalDescriptionStyle.current,
    title: String,
    subtitle: String
) = Description(
    style = style.style,
    title = title,
    titleStyle = style.titleStyle,
    subtitle = subtitle,
    subtitleStyle = style.subtitleStyle
)

/**
 * The style arguments associated with a [Description] composable.
 */
data class DescriptionStyle(
    /**
     * The style of the container.
     */
    val style: ColumnStyle = ColumnStyle.Default,

    /**
     * The style of the tile.
     */
    val titleStyle: WordStyle = WordStyle.Default,

    /**
     * The style of the subtitle.
     */
    val subtitleStyle: WordStyle = WordStyle.Default
) : Style<DescriptionStyle> {
    companion object {
        @Stable
        val Default = DescriptionStyle()
    }

    override fun merge(other: DescriptionStyle?): DescriptionStyle = if (other == null) this else DescriptionStyle(
        style = style.merge(other.style),
        titleStyle = titleStyle.merge(other.titleStyle),
        subtitleStyle = subtitleStyle.merge(other.subtitleStyle)
    )

    @Composable
    override fun localized(): DescriptionStyle = DescriptionStyle(
        style = LocalColumnStyle.current,
        titleStyle = WordStyle(
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Visible,
            textStyle = MaterialTheme.typography.subtitle1
        ).merge(LocalWordStyle.current),
        subtitleStyle = WordStyle(
            overflow = TextOverflow.Ellipsis,
            textStyle = MaterialTheme.typography.subtitle2
        ).merge(LocalWordStyle.current)
    ).merge(this)
}