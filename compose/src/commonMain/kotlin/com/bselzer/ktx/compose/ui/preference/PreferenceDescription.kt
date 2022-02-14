package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.description.Description
import com.bselzer.ktx.compose.ui.description.DescriptionStyle
import com.bselzer.ktx.compose.ui.description.LocalDescriptionStyle
import com.bselzer.ktx.compose.ui.style.ColumnStyle
import com.bselzer.ktx.compose.ui.style.LocalSpacing
import com.bselzer.ktx.compose.ui.unit.specified

/**
 * Lays out a description of the state of the preference through a [title] and [subtitle].
 *
 * @param ref the constraint reference
 * @param startRef the starting constraint reference, or null to use the parent
 * @param endRef the ending constraint reference, or null to use the parent
 * @param spacing the spacing between components
 * @param style the style describing how to layout the description
 * @param title the name of the preference
 * @param subtitle the description of the preference
 */
@Composable
fun ConstraintLayoutScope.PreferenceDescription(
    ref: ConstrainedLayoutReference = createRef(),
    startRef: ConstrainedLayoutReference? = null,
    endRef: ConstrainedLayoutReference? = null,
    style: DescriptionStyle = LocalDescriptionStyle.current,
    title: String,
    subtitle: String,
    spacing: Dp = LocalSpacing.current,
) {
    val margin = spacing.specified(default = PreferenceSpacing)
    Description(
        title = title,
        subtitle = subtitle,
        style = DescriptionStyle(
            style = ColumnStyle(modifier = Modifier.constrainAs(ref = ref) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(anchor = startRef?.end ?: parent.start, margin = margin)
                end.linkTo(anchor = endRef?.start ?: parent.end, margin = margin)
                width = Dimension.fillToConstraints
            })
        ).merge(style)
    )
}


