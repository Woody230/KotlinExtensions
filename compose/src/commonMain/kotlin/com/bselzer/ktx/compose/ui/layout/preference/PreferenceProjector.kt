package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.description.DescriptionProjector
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class PreferenceProjector(
    override val interactor: PreferenceInteractor,
    override val presenter: PreferencePresenter = PreferencePresenter.Default
) : Projector<PreferenceInteractor, PreferencePresenter>() {
    private val imageProjector = ImageProjector(interactor.image, presenter.image)
    private val descriptionProjector = DescriptionProjector(interactor.description, presenter.description)

    companion object {
        @Composable
        internal fun ConstraintLayoutScope.PreferenceImage(
            ref: ConstrainedLayoutReference,
            image: ImageProjector
        ) = image.project(
            modifier = Modifier.constrainAs(ref = ref) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )
    }

    /**
     * @param ending the composable function for laying out the end of the preference next to the description
     */
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        ending: (@Composable () -> Unit)? = null,
    ) = contextualize(modifier) { combinedModifier ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .then(combinedModifier)
        ) {
            val (icon, description, box) = createRefs()
            PreferenceImage(ref = icon, image = imageProjector)

            PreferenceDescription(
                ref = description,
                startRef = icon,
                endRef = if (ending == null) null else box,
            )

            ending?.let { ending ->
                Box(
                    modifier = Modifier.constrainAs(box) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                ) {
                    ending.invoke()
                }
            }
        }
    }

    @Composable
    private fun ConstraintLayoutScope.PreferenceDescription(
        ref: ConstrainedLayoutReference,
        startRef: ConstrainedLayoutReference? = null,
        endRef: ConstrainedLayoutReference? = null,
    ) = descriptionProjector.project(
        modifier = Modifier.constrainAs(ref = ref) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(anchor = startRef?.end ?: parent.start, margin = PreferenceConstants.Spacing)
            end.linkTo(anchor = endRef?.start ?: parent.end, margin = PreferenceConstants.Spacing)
            width = Dimension.fillToConstraints
        }
    )
}