package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.bselzer.ktx.compose.ui.style.Image
import com.bselzer.ktx.compose.ui.style.ImageStyle
import com.bselzer.ktx.compose.ui.style.LocalImageStyle
import com.bselzer.ktx.compose.ui.style.localized

/**
 * Lays out an image related to preferences.
 *
 * @param ref the constraint reference
 * @param painter the painter to draw
 * @param contentDescription the description for accessibility services
 * @param style the style describing how to lay out the icon
 */
@Composable
fun ConstraintLayoutScope.PreferenceImage(
    ref: ConstrainedLayoutReference,
    painter: Painter,
    contentDescription: String,
    style: ImageStyle = LocalImageStyle.localized()
) = PreferenceImage(
    painter = painter,
    contentDescription = contentDescription,
    style = ImageStyle(
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(48.dp, 48.dp)
            .constrainAs(ref = ref) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            },
    ).merge(style)
)

/**
 * Lays out an image related to preferences.
 *
 * @param painter the painter to draw
 * @param contentDescription the description for accessibility services
 * @param style the style describing how to lay out the icon
 */
@Composable
fun PreferenceImage(
    painter: Painter,
    contentDescription: String,
    style: ImageStyle = LocalImageStyle.current
) = Image(
    painter = painter,
    contentDescription = contentDescription,
    style = style
)