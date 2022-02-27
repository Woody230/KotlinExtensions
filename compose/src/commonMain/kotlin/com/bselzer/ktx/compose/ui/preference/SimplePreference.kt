package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bselzer.ktx.compose.ui.description.DescriptionStyle
import com.bselzer.ktx.compose.ui.description.LocalDescriptionStyle
import com.bselzer.ktx.compose.ui.style.ImageStyle
import com.bselzer.ktx.compose.ui.style.LocalImageStyle
import com.bselzer.ktx.compose.ui.style.localized

/**
 * Lays out a preference designed to display an icon, description, and optional content meant to take up a small amount of space.
 *
 * @param modifier the preference modifier
 * @param spacing the spacing between components
 * @param painter the painter for displaying the image
 * @param imageStyle the style describing how to lay out the image
 * @param descriptionStyle the style describing how to lay out the description
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param onClick the on-click handler
 * @param ending the optional block for laying out content at the end of the preference
 */
@Composable
fun SimplePreference(
    modifier: Modifier = Modifier,
    spacing: Dp = PreferenceSpacing,
    painter: Painter,
    imageStyle: ImageStyle = LocalImageStyle.localized(),
    descriptionStyle: DescriptionStyle = LocalDescriptionStyle.localized(),
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    ending: (@Composable () -> Unit)? = null,
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .then(modifier)
) {
    val (icon, description, box) = createRefs()
    PreferenceImage(
        ref = icon,
        contentDescription = title,
        painter = painter,
        style = imageStyle
    )

    PreferenceDescription(
        ref = description,
        style = descriptionStyle,
        startRef = icon,
        endRef = box,
        spacing = spacing,
        title = title,
        subtitle = subtitle,
    )

    Box(
        modifier = Modifier.constrainAs(box) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }
    ) {
        ending?.invoke()
    }
}