package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.description.DescriptionStyle
import com.bselzer.ktx.compose.ui.description.LocalDescriptionStyle
import com.bselzer.ktx.compose.ui.style.ImageStyle
import com.bselzer.ktx.compose.ui.style.LocalImageStyle
import com.bselzer.ktx.compose.ui.style.ModifiableStyle

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
    spacing = style.spacing,
    painter = painter,
    imageStyle = style.imageStyle,
    descriptionStyle = style.descriptionStyle,
    title = title,
    subtitle = subtitle,
    onClick = onClick,
    ending = ending
)

/**
 * Creates a localized [SimplePreferenceStyle].
 */
@Composable
fun simplePreferenceStyle(): SimplePreferenceStyle = SimplePreferenceStyle(
    imageStyle = LocalImageStyle.current,
    descriptionStyle = LocalDescriptionStyle.current
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
    val spacing: Dp = 25.dp,

    /**
     * The style describing how to lay out the image
     */
    val imageStyle: ImageStyle,

    /**
     * The style describing how to lay out the description
     */
    val descriptionStyle: DescriptionStyle
): ModifiableStyle