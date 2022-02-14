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
import com.bselzer.ktx.compose.ui.description.LocalDescriptionStyle
import com.bselzer.ktx.compose.ui.style.LocalImageStyle
import com.bselzer.ktx.compose.ui.style.LocalSpacing
import com.bselzer.ktx.compose.ui.style.LocalWordStyle
import com.bselzer.ktx.compose.ui.style.WordStyle
import com.bselzer.ktx.compose.ui.unit.specified

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
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
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
        style = style.imageStyle ?: LocalImageStyle.current
    )

    val descriptionStyle = style.descriptionStyle ?: LocalDescriptionStyle.current
    val titleStyle = descriptionStyle.titleStyle ?: LocalWordStyle.current
    val subtitleStyle = descriptionStyle.subtitleStyle ?: LocalWordStyle.current
    val margin = (style.spacing ?: LocalSpacing.current).specified(PreferenceSpacing)
    DescriptionTitle(
        title = title,
        style = WordStyle(
            modifier = Modifier.constrainAs(descriptionTitle) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(icon.end, margin = margin)
            },
        ).merge(titleStyle)
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
        ).merge(subtitleStyle)
    )
}