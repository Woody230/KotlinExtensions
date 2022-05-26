package com.bselzer.ktx.compose.ui.layout.appbar.top

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class TopAppBarProjector(
    interactor: TopAppBarInteractor,
    presenter: TopAppBarPresenter = TopAppBarPresenter.Default
) : Projector<TopAppBarInteractor, TopAppBarPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val titleProjector = TextProjector(interactor.title, presenter.title)
        val navigationProjector = interactor.navigation?.let { IconButtonProjector(it, presenter.icon) }
        val actionProjector = interactor.actions.map { IconButtonProjector(it, presenter.icon) }

        TopAppBar(
            title = { titleProjector.Projection() },
            modifier = combinedModifier,
            navigationIcon = navigationProjector?.let { navigation -> { navigation.Projection() } },
            actions = { actionProjector.forEach { action -> action.Projection() } },
            backgroundColor = presenter.backgroundColor,
            contentColor = presenter.contentColor,
            elevation = presenter.elevation
        )
    }
}