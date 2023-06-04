package com.bselzer.ktx.compose.ui.layout.preference.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceConstants
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector.Companion.PreferenceImage
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class TextPreferenceProjector(
    interactor: TextPreferenceInteractor,
    presenter: TextPreferencePresenter = TextPreferencePresenter.Default
) : Projector<TextPreferenceInteractor, TextPreferencePresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .then(combinedModifier)
        ) {
            val imageProjector = ImageProjector(interactor.image, presenter.image)
            val titleProjector = TextProjector(interactor.title, presenter.title)
            val subtitleProjector = TextProjector(interactor.subtitle, presenter.subtitle)

            val (icon, descriptionTitle, descriptionSubtitle) = createRefs()
            PreferenceImage(ref = icon, imageProjector = imageProjector)

            DisjointPreferenceDescription(
                titleRef = descriptionTitle,
                subtitleRef = descriptionSubtitle,
                startRef = icon,
                titleProjector = titleProjector,
                subtitleProjector = subtitleProjector
            )
        }
    }

    @Composable
    private fun ConstraintLayoutScope.DisjointPreferenceDescription(
        titleRef: ConstrainedLayoutReference,
        subtitleRef: ConstrainedLayoutReference,
        startRef: ConstrainedLayoutReference,
        titleProjector: TextProjector,
        subtitleProjector: TextProjector
    ) {
        titleProjector.Projection(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(startRef.end, margin = PreferenceConstants.Spacing)
            }
        )
        subtitleProjector.Projection(
            modifier = Modifier.constrainAs(subtitleRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(titleRef.end, margin = PreferenceConstants.Spacing)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}