package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope

/**
 * Lays out an icon image related to preferences.
 *
 * @param ref the constraint reference
 * @param painter the painter to draw
 * @param contentDescription the description for accessibility services
 * @param contentSize the size of the image
 * @param contentScale how to scale the image
 */
@Composable
fun ConstraintLayoutScope.PreferenceIcon(
    ref: ConstrainedLayoutReference = createRef(),
    painter: Painter,
    contentDescription: String,
    contentSize: DpSize = DpSize(48.dp, 48.dp),
    contentScale: ContentScale = ContentScale.FillBounds,
) = PreferenceIcon(
    painter = painter,
    contentDescription = contentDescription,
    contentSize = contentSize,
    contentScale = contentScale,
    modifier = Modifier
        .constrainAs(ref = ref) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
)

/**
 * Lays out an icon image related to preferences.
 *
 * @param modifier the [Image] modifier
 * @param painter the painter to draw
 * @param contentDescription the description for accessibility services
 * @param contentSize the size of the image
 * @param contentScale how to scale the image
 */
@Composable
fun PreferenceIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    contentSize: DpSize = DpSize(48.dp, 48.dp),
    contentScale: ContentScale = ContentScale.FillBounds,
) = Image(
    painter = painter,
    contentDescription = contentDescription,
    contentScale = contentScale,
    modifier = Modifier
        .size(contentSize)
        .then(modifier)
)