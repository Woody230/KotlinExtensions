package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Badge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BadgeProjector(
    interactor: BadgeInteractor = BadgeInteractor.Default,
    presenter: BadgePresenter = BadgePresenter.Default
) : Projector<BadgeInteractor, BadgePresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable (RowScope.() -> Unit)? = null
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Badge(
            modifier = combinedModifier,
            backgroundColor = presenter.backgroundColor,
            contentColor = presenter.contentColor,
            content = content
        )
    }
}