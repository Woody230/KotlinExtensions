package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

class PreferenceProjector(
    interactor: PreferenceInteractor,
    presenter: PreferencePresenter = PreferencePresenter.Default
) : BasePreferenceProjector(interactor, presenter) {
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
            Image(ref = icon)

            Description(
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
}