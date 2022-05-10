package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.description.DescriptionProjector
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

sealed class BasePreferenceProjector(
    final override val interactor: PreferenceInteractor,
    final override val presenter: PreferencePresenter = PreferencePresenter.Default
) : Projector<PreferenceInteractor, PreferencePresenter>() {
    private val imageProjector = ImageProjector(interactor.image, presenter.image)
    private val descriptionProjector = DescriptionProjector(interactor.description, presenter.description)

    companion object {
        private val Spacing = 25.dp
    }

    @Composable
    protected fun ConstraintLayoutScope.Image(
        ref: ConstrainedLayoutReference,
    ) = imageProjector.project(
        modifier = Modifier.constrainAs(ref = ref) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
    )

    @Composable
    protected fun ConstraintLayoutScope.Description(
        ref: ConstrainedLayoutReference,
        startRef: ConstrainedLayoutReference? = null,
        endRef: ConstrainedLayoutReference? = null,
    ) = descriptionProjector.project(
        modifier = Modifier.constrainAs(ref = ref) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(anchor = startRef?.end ?: parent.start, margin = Spacing)
            end.linkTo(anchor = endRef?.start ?: parent.end, margin = Spacing)
            width = Dimension.fillToConstraints
        }
    )

    @Composable
    protected fun ConstraintLayoutScope.DisjointDescription(
        titleRef: ConstrainedLayoutReference,
        subtitleRef: ConstrainedLayoutReference,
        startRef: ConstrainedLayoutReference,
    ) = descriptionProjector.project(
        title = Modifier.constrainAs(titleRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(startRef.end, margin = Spacing)
        },
        subtitle = Modifier.constrainAs(subtitleRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(titleRef.end, margin = Spacing)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
    )
}