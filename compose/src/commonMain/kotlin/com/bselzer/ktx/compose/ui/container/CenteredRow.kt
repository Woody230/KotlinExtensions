package com.bselzer.ktx.compose.ui.container

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.style.LocalWordStyle
import com.bselzer.ktx.compose.ui.style.Text
import com.bselzer.ktx.compose.ui.style.WordStyle

/**
 * Lays out a row with values aligned from the center with [spacing].
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param startValue the introductory value
 * @param endValue the descriptive value
 * @param spacing the size of the spacing in between the [startValue] and [endValue]
 * @param startStyle the text style of the [startValue]
 * @param endStyle the text style of the [endValue]
 */
@Composable
fun CenteredRow(
    modifier: Modifier = Modifier,
    startValue: String,
    endValue: String,
    spacing: Dp = 5.dp,
    startStyle: WordStyle = LocalWordStyle.current,
    endStyle: WordStyle = LocalWordStyle.current
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .then(modifier)
) {
    val (left, spacer, right) = createRefs()
    Text(
        text = startValue,
        style = startStyle.merge(
            // These properties take priority so merge them as the other.
            WordStyle(
                textAlign = TextAlign.Right,
                modifier = Modifier.constrainAs(left) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(spacer.start)
                    width = Dimension.fillToConstraints
                }
            )
        ),
    )
    Spacer(
        modifier = Modifier
            .width(spacing)
            .constrainAs(spacer) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(left.end)
                end.linkTo(right.start)
            }
    )
    Text(
        text = endValue,
        style = endStyle.merge(
            // These properties take priority so merge them as the other.
            WordStyle(
                textAlign = TextAlign.Left,
                modifier = Modifier.constrainAs(right) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(spacer.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )
        )
    )
}