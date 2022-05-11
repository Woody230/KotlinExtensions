package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Badge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BadgeProjector(
    override val interactor: BadgeInteractor = BadgeInteractor.Default,
    override val presenter: BadgePresenter = BadgePresenter.Default
) : Projector<BadgeInteractor, BadgePresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable (RowScope.() -> Unit)? = null
    ) = contextualize(modifier) { combinedModifier ->
        Badge(
            modifier = combinedModifier,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            content = content
        )
    }
}