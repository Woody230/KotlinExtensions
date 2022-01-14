package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.description.DescriptionSubtitle
import com.bselzer.ktx.compose.ui.description.DescriptionTitle

/**
 * Lays out the description of a preference with the title on the starting side and the subtitle and the ending side.
 *
 * @param modifier the preference modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param onClick the on-click handler
 */
@Composable
fun TextPreference(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    onClick: () -> Unit
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .then(modifier)
) {
    val (icon, descriptionTitle, descriptionSubtitle) = createRefs()
    PreferenceIcon(
        ref = icon,
        contentDescription = title,
        painter = iconPainter,
        contentScale = iconScale,
        contentSize = iconSize,
    )

    DescriptionTitle(
        modifier = Modifier.constrainAs(descriptionTitle) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(icon.end, margin = spacing)
        },
        title = title,
        style = titleStyle
    )

    DescriptionSubtitle(
        modifier = Modifier.constrainAs(descriptionSubtitle) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(descriptionTitle.end, margin = spacing)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        },
        subtitle = subtitle,
        style = subtitleStyle
    )
}