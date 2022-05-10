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
    private val titleProjection = TextProjector(interactor.title, presenter.title)
    private val navigationProjection = interactor.navigation?.let { navigation -> IconButtonProjector(navigation, presenter.icon) }
    private val actionProjection = interactor.actions.map { action -> IconButtonProjector(action, presenter.icon) }

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        TopAppBar(
            title = { titleProjection.project() },
            modifier = modifier,
            navigationIcon = navigationProjection?.let { navigation -> { navigation.project() } },
            actions = { actionProjection.forEach { action -> action.project() } },
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation
        )
    }
}