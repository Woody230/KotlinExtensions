package com.bselzer.ktx.compose.ui.layout.badgetext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.badge.BadgeProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class BadgeTextProjector(
    interactor: BadgeTextInteractor,
    presenter: BadgeTextPresenter = BadgeTextPresenter.Default
) : Projector<BadgeTextInteractor, BadgeTextPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val badgeProjector = BadgeProjector(interactor.badge, presenter.badge)
        val textProjector = TextProjector(interactor.text, presenter.text)

        badgeProjector.Projection(
            modifier = combinedModifier
        ) {
            textProjector.Projection()
        }
    }
}