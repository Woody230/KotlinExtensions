package com.bselzer.ktx.compose.ui.layout.centeredtext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerDirection
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerProjection
import com.bselzer.ktx.compose.ui.layout.text.TextProjection

class CenteredTextProjection(
    override val logic: CenteredTextLogic,
    override val presentation: CenteredTextPresentation = CenteredTextPresentation.Default
) : Projector<CenteredTextLogic, CenteredTextPresentation>() {
    private val startProjection = TextProjection(logic.start, presentation.text)
    private val endProjection = TextProjection(logic.end, presentation.text)
    private val spacerProjection = SpacerProjection(logic.spacer, presentation.spacer)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = ConstraintLayout(
        modifier = modifier
    ) {
        // TODO consider layout direction (both vertical/horizontal and start/end)
        val (left, spacer, right) = createRefs()
        startProjection.project(
            textAlign = TextAlign.Right,
            modifier = Modifier.constrainAs(left) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(spacer.start)
                width = Dimension.fillToConstraints
            }
        )
        spacerProjection.project(
            direction = SpacerDirection.HORIZONTAL,
            modifier = Modifier.constrainAs(spacer) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(left.end)
                end.linkTo(right.start)
            }
        )
        endProjection.project(
            textAlign = TextAlign.Left,
            modifier = Modifier.constrainAs(right) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(spacer.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}