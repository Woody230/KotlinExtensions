package com.bselzer.ktx.compose.ui.description

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
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
    style = style.style ?: LocalColumnStyle.current,
    title = title,
    titleStyle = style.titleStyle ?: LocalWordStyle.current,
    subtitle = subtitle,
    subtitleStyle = style.subtitleStyle ?: LocalWordStyle.current
)

/**
 * The style arguments associated with a [Description] composable.
 */
data class DescriptionStyle(
    val style: ColumnStyle? = null,
    val titleStyle: WordStyle? = null,
    val subtitleStyle: WordStyle? = null
) : Style<DescriptionStyle> {
    companion object {
        @Stable
        val Default = DescriptionStyle()
    }

    override fun merge(other: DescriptionStyle?): DescriptionStyle = if (other == null) this else DescriptionStyle(
        style = style?.merge(other.style) ?: other.style,
        titleStyle = titleStyle?.merge(other.titleStyle) ?: other.titleStyle,
        subtitleStyle = subtitleStyle?.merge(other.subtitleStyle) ?: other.subtitleStyle
    )
}