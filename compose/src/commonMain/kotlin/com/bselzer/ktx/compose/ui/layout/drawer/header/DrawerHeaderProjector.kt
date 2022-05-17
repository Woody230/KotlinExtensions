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
    override val interactor: DrawerHeaderInteractor,
    override val presenter: DrawerHeaderPresenter = DrawerHeaderPresenter.Default
) : Projector<DrawerHeaderInteractor, DrawerHeaderPresenter>() {
    private val containerProjector = ColumnProjector(interactor.container, presenter.container)
    private val imageProjector = interactor.image?.let { image -> ImageProjector(image, presenter.image) }
    private val descriptionProjector = interactor.description?.let { description -> DescriptionProjector(description, presenter.description) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        containerProjector.Projection(
            modifier = combinedModifier,
            content = buildArray {
                addIfNotNull(imageProjector) { it.Projection() }
                addIfNotNull(descriptionProjector) { it.Projection(modifier = Modifier.weight(1f)) }
            }
        )
    }
}