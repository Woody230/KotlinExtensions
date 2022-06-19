package com.bselzer.ktx.compose.ui.layout.drawer.header

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.column.addIfNotNull
import com.bselzer.ktx.compose.ui.layout.description.DescriptionProjector
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.function.collection.buildArray

class DrawerHeaderProjector(
    interactor: DrawerHeaderInteractor,
    presenter: DrawerHeaderPresenter = DrawerHeaderPresenter.Default
) : Projector<DrawerHeaderInteractor, DrawerHeaderPresenter>(interactor, presenter) {


    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val containerProjector = ColumnProjector(interactor.container, presenter.container)
        val imageProjector = interactor.image?.let { ImageProjector(it, presenter.image) }
        val descriptionProjector = interactor.description?.let { DescriptionProjector(it, presenter.description) }

        containerProjector.Projection(
            modifier = combinedModifier,
            content = buildArray {
                addIfNotNull(imageProjector) { it.Projection() }
                addIfNotNull(descriptionProjector) { it.Projection(modifier = Modifier.weight(1f)) }
            }
        )
    }
}