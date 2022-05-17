package com.bselzer.ktx.compose.ui.layout.drawer.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.column.addIfNotNull
import com.bselzer.ktx.compose.ui.layout.drawer.component.DrawerComponentProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.function.collection.buildArray

class DrawerSectionProjector(
    override val interactor: DrawerSectionInteractor,
    override val presenter: DrawerSectionPresenter = DrawerSectionPresenter.Default
) : Projector<DrawerSectionInteractor, DrawerSectionPresenter>() {
    private val containerProjector = ColumnProjector(interactor.container, presenter.container)
    private val titleProjector = interactor.title?.let { title -> TextProjector(title, presenter.component.text) }
    private val componentProjectors = interactor.components.map { component -> DrawerComponentProjector(component, presenter.component) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        containerProjector.Projection(
            modifier = combinedModifier,
            content = buildArray {
                addIfNotNull(titleProjector) { it.Projection() }
                componentProjectors.forEach {
                    add { it.Projection() }
                }
            }
        )
    }
}