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
    private val containerProjector = ColumnProjector(interactor.container, presenter.container)
    private val titleProjector = TextProjector(interactor.title, presenter.title)
    private val subtitleProjector = TextProjector(interactor.subtitle, presenter.subtitle)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        containerProjector.project(
            modifier = combinedModifier,
            { titleProjector.project() },
            { subtitleProjector.project() }
        )
    }

    @Composable
    internal fun project(
        title: Modifier = Modifier,
        subtitle: Modifier = Modifier
    ) {
        titleProjector.project(modifier = title)
        subtitleProjector.project(modifier = subtitle)
    }
}