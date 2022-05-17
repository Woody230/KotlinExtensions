package com.bselzer.ktx.compose.ui.layout.description

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.column.addIfNotNull
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.function.collection.buildArray

class DescriptionProjector(
    interactor: DescriptionInteractor,
    presenter: DescriptionPresenter = DescriptionPresenter.Default
) : Projector<DescriptionInteractor, DescriptionPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val containerProjector = ColumnProjector(interactor.container, presenter.container)
        val titleProjector = TextProjector(interactor.title, presenter.title)
        val subtitleProjector = interactor.subtitle?.let { TextProjector(it, presenter.subtitle) }

        containerProjector.Projection(
            modifier = combinedModifier,
            content = buildArray {
                add { titleProjector.Projection() }
                addIfNotNull(subtitleProjector) { it.Projection() }
            }
        )
    }
}