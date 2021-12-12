package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension

/**
 * Lays out a description of the state of the preference through a [title] and [subtitle].
 *
 * @param modifier the description modifier
 * @param ref the constraint reference
 * @param startRef the starting constraint reference, or null to use the parent
 * @param endRef the ending constraint reference, or null to use the parent
 * @param spacing the spacing between components
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 */
@Composable
fun ConstraintLayoutScope.PreferenceDescription(
    modifier: Modifier = Modifier,
    ref: ConstrainedLayoutReference = createRef(),
    startRef: ConstrainedLayoutReference? = null,
    endRef: ConstrainedLayoutReference? = null,
    spacing: Dp = 25.dp,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2
) = com.bselzer.ktx.compose.ui.preference.PreferenceDescription(
    title = title,
    titleStyle = titleStyle,
    subtitle = subtitle,
    subtitleStyle = subtitleStyle,
    modifier = modifier
        .constrainAs(ref = ref) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(anchor = startRef?.end ?: parent.start, margin = spacing)
            end.linkTo(anchor = endRef?.start ?: parent.end, margin = spacing)
            width = Dimension.fillToConstraints
        }
)

/**
 * Lays out a description of the state of the preference through a [title] and [subtitle].
 *
 * @param modifier the description modifier
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 */
@Composable
fun PreferenceDescription(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2
) = Column(
    modifier = modifier
) {
    PreferenceTitle(title = title, style = titleStyle)
    PreferenceSubtitle(subtitle = subtitle, style = subtitleStyle)
}

/**
 * Lays out a title designed for the name of a preference.
 *
 * @param modifier the [Text] modifier
 * @param title the name of the preference
 * @param style the style of the text for displaying the [title]
 */
@Composable
fun PreferenceTitle(modifier: Modifier = Modifier, title: String, style: TextStyle = MaterialTheme.typography.subtitle1) =
    Text(modifier = modifier, text = title, fontWeight = FontWeight.Bold, style = style)

/**
 * Lays out a subtitle designed for the description of a preference.
 *
 * @param modifier the [Text] modifier
 * @param subtitle the description of the preference
 * @param style the style of the text for displaying the [subtitle]
 */
@Composable
fun PreferenceSubtitle(modifier: Modifier = Modifier, subtitle: String, style: TextStyle = MaterialTheme.typography.subtitle2) =
    Text(modifier = modifier, text = subtitle, style = style)
