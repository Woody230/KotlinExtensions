package com.bselzer.ktx.compose.ui.layout.centeredtext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerDirection
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerProjector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class CenteredTextProjector(
    override val interactor: CenteredTextInteractor,
    override val presenter: CenteredTextPresenter = CenteredTextPresenter.Default
) : Projector<CenteredTextInteractor, CenteredTextPresenter>() {
    private val startProjection = TextProjector(interactor.start, presenter.text)
    private val endProjection = TextProjector(interactor.end, presenter.text)
    private val spacerProjection = SpacerProjector(interactor.spacer, presenter.spacer)

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