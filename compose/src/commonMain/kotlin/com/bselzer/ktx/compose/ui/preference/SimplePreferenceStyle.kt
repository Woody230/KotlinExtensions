package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.description.DescriptionStyle
import com.bselzer.ktx.compose.ui.description.LocalDescriptionStyle
import com.bselzer.ktx.compose.ui.style.ImageStyle
import com.bselzer.ktx.compose.ui.style.LocalImageStyle
import com.bselzer.ktx.compose.ui.style.LocalSpacing
import com.bselzer.ktx.compose.ui.style.ModifiableStyle
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred SimplePreferenceStyle that will be used by SimplePreference components by default.
 */
val LocalSimplePreferenceStyle: ProvidableCompositionLocal<SimplePreferenceStyle> = compositionLocalOf { SimplePreferenceStyle.Default }

/**
 * A wrapper around the [SimplePreference] composable.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param onClick the on-click handler
 * @param ending the optional block for laying out content at the end of the preference
 */
@Composable
fun SimplePreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    ending: (@Composable () -> Unit)? = null,
) = SimplePreference(
    modifier = style.modifier,
    spacing = style.spacing ?: LocalSpacing.current,
    painter = painter,
    imageStyle = style.imageStyle ?: LocalImageStyle.current,
    descriptionStyle = style.descriptionStyle ?: LocalDescriptionStyle.current,
    title = title,
    subtitle = subtitle,
    onClick = onClick,
    ending = ending
)

/**
 * The style arguments associated with a [SimplePreference] composable.
 */
data class SimplePreferenceStyle(
    /**
     * Modifier to apply to the layout node.
     */
    override val modifier: Modifier = Modifier,

    /**
     * The spacing between components.
     */
    val spacing: Dp? = null,

    /**
     * The style describing how to lay out the image
     */
    val imageStyle: ImageStyle? = null,

    /**
     * The style describing how to lay out the description
     */
    val descriptionStyle: DescriptionStyle? = null
): ModifiableStyle<SimplePreferenceStyle> {
    companion object {
        @Stable
        val Default = SimplePreferenceStyle()
    }

    override fun merge(other: SimplePreferenceStyle?): SimplePreferenceStyle = if (other == null) this else SimplePreferenceStyle(
        modifier = modifier.then(other.modifier),
        spacing = spacing.merge(other.spacing),
        imageStyle = imageStyle?.merge(other.imageStyle) ?: other.imageStyle,
        descriptionStyle = descriptionStyle?.merge(other.descriptionStyle) ?: other.descriptionStyle
    )
}