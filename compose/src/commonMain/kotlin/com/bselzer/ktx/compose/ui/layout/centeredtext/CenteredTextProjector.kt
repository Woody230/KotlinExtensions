package com.bselzer.ktx.compose.ui.layout.centeredtext

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerProjector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class CenteredTextProjector(
    override val interactor: CenteredTextInteractor,
    override val presenter: CenteredTextPresenter = CenteredTextPresenter.Default
) : Projector<CenteredTextInteractor, CenteredTextPresenter>() {
    private val startProjector = TextProjector(interactor.start, presenter.text)
    private val endProjector = TextProjector(interactor.end, presenter.text)
    private val spacerProjector = SpacerProjector(interactor.spacer, presenter.spacer)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        ConstraintLayout(
            modifier = combinedModifier
        ) {
            // TODO consider layout direction (both vertical/horizontal and start/end)
            val (left, spacer, right) = createRefs()
            startProjector.Projection(
                textAlign = TextAlign.Right,
                modifier = Modifier.constrainAs(left) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(spacer.start)
                    width = Dimension.fillToConstraints
                }
            )
            spacerProjector.Projection(
                modifier = Modifier
                    .defaultMinSize(minWidth = 5.dp)
                    .constrainAs(spacer) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(left.end)
                        end.linkTo(right.start)
                    }
            )
            endProjector.Projection(
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
}