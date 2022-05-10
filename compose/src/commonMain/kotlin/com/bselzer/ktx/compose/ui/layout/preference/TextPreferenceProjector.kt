package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

class TextPreferenceProjector(
    interactor: PreferenceInteractor,
    presenter: PreferencePresenter = PreferencePresenter.Default
) : BasePreferenceProjector(interactor, presenter) {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .then(combinedModifier)
        ) {
            val (icon, descriptionTitle, descriptionSubtitle) = createRefs()
            Image(ref = icon)

            DisjointDescription(
                titleRef = descriptionTitle,
                subtitleRef = descriptionSubtitle,
                startRef = icon
            )
        }
    }
}