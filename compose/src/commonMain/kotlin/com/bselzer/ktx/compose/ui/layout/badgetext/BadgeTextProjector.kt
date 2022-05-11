package com.bselzer.ktx.compose.ui.layout.badgetext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.badge.BadgeProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class BadgeTextProjector(
    override val interactor: BadgeTextInteractor,
    override val presenter: BadgeTextPresenter = BadgeTextPresenter.Default
) : Projector<BadgeTextInteractor, BadgeTextPresenter>() {
    private val badgeProjector = BadgeProjector(interactor.badge, presenter.badge)
    private val textProjector = TextProjector(interactor.text, presenter.text)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        badgeProjector.project(
            modifier = combinedModifier
        ) {
            textProjector.project()
        }
    }
}