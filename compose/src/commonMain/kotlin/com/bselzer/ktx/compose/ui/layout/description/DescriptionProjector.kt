package com.bselzer.ktx.compose.ui.layout.description

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class DescriptionProjector(
    override val interactor: DescriptionInteractor,
    override val presenter: DescriptionPresenter = DescriptionPresenter.Default
) : Projector<DescriptionInteractor, DescriptionPresenter>() {
    private val containerProjection = ColumnProjector(interactor.container, presenter.container)
    private val titleProjection = TextProjector(interactor.title, presenter.title)
    private val subtitleProjection = TextProjector(interactor.subtitle, presenter.subtitle)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = containerProjection.project(
        modifier = modifier,
        { titleProjection.project() },
        { subtitleProjection.project() }
    )
}