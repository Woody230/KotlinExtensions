package com.bselzer.ktx.compose.ui.layout.drawer.section

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.drawer.component.DrawerComponentProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.function.collection.buildArray

class DrawerSectionProjector(
    interactor: DrawerSectionInteractor,
    presenter: DrawerSectionPresenter = DrawerSectionPresenter.Default
) : Projector<DrawerSectionInteractor, DrawerSectionPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val containerProjector = ColumnProjector(interactor.container, presenter.container)
        val titleProjector = interactor.title?.let { TextProjector(it, presenter.title) }
        val componentProjectors = interactor.components.map { DrawerComponentProjector(it, presenter.component) }

        Column(modifier = combinedModifier) {
            titleProjector?.Projection()

            containerProjector.Projection(
                content = buildArray {
                    componentProjectors.forEach {
                        add { it.Projection() }
                    }
                }
            )
        }
    }
}