package com.bselzer.ktx.compose.ui.layout.topappbar

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class TopAppBarProjector(
    override val interactor: TopAppBarInteractor,
    override val presenter: TopAppBarPresenter = TopAppBarPresenter.Default
) : Projector<TopAppBarInteractor, TopAppBarPresenter>() {
    private val titleProjector = TextProjector(interactor.title, presenter.title)
    private val navigationProjector = interactor.navigation?.let { navigation -> IconButtonProjector(navigation, presenter.icon) }
    private val actionProjector = interactor.actions.map { action -> IconButtonProjector(action, presenter.icon) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        TopAppBar(
            title = { titleProjector.Projection() },
            modifier = combinedModifier,
            navigationIcon = navigationProjector?.let { navigation -> { navigation.Projection() } },
            actions = { actionProjector.forEach { action -> action.Projection() } },
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation
        )
    }
}