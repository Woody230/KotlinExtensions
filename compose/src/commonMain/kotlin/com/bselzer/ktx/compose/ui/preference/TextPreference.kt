package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.description.DescriptionSubtitle
import com.bselzer.ktx.compose.ui.description.DescriptionTitle
import com.bselzer.ktx.compose.ui.style.WordStyle
import com.bselzer.ktx.compose.ui.style.localized

/**
 * Lays out the description of a preference with the title on the starting side and the subtitle and the ending side.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param onClick the on-click handler
 */
@Composable
fun TextPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.localized(),
    painter: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .then(style.modifier)
) {
    val (icon, descriptionTitle, descriptionSubtitle) = createRefs()
    PreferenceImage(
        ref = icon,
        contentDescription = title,
        painter = painter,
        style = style.imageStyle
    )

    val descriptionStyle = style.descriptionStyle
    val margin = style.spacing
    DescriptionTitle(
        title = title,
        style = WordStyle(
            modifier = Modifier.constrainAs(descriptionTitle) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(icon.end, margin = margin)
            },
        ).merge(descriptionStyle.titleStyle)
    )

    DescriptionSubtitle(
        subtitle = subtitle,
        style = WordStyle(
            Modifier.constrainAs(descriptionSubtitle) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(descriptionTitle.end, margin = margin)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
        ).merge(descriptionStyle.subtitleStyle)
    )
}