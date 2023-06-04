package com.bselzer.ktx.compose.ui.layout.drawer.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.row.RowProjector
import com.bselzer.ktx.compose.ui.layout.row.addIfNotNull
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.function.collection.buildArray

class DrawerComponentProjector(
    interactor: DrawerComponentInteractor,
    presenter: DrawerComponentPresenter = DrawerComponentPresenter.Default
) : Projector<DrawerComponentInteractor, DrawerComponentPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val containerProjector = RowProjector(interactor.container, presenter.container)
        val iconProjector = interactor.icon?.let { IconProjector(it, presenter.icon) }
        val textProjector = TextProjector(interactor.text, presenter.text)

        containerProjector.Projection(
            modifier = combinedModifier,
            content = buildArray {
                addIfNotNull(iconProjector) { it.Projection() }
                add { textProjector.Projection(modifier = Modifier.weight(1f)) }
            }
        )
    }
}