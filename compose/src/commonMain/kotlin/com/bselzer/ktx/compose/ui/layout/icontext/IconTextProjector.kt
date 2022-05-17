package com.bselzer.ktx.compose.ui.layout.icontext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.row.RowProjector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class IconTextProjector(
    interactor: IconTextInteractor,
    presenter: IconTextPresenter = IconTextPresenter.Default
) : Projector<IconTextInteractor, IconTextPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val containerProjector = RowProjector(interactor.container, presenter.container)
        val iconProjector = IconProjector(interactor.icon, presenter.icon)
        val textProjector = TextProjector(interactor.text, presenter.text)

        containerProjector.Projection(
            modifier = combinedModifier,
            { iconProjector.Projection() },
            { textProjector.Projection(modifier = Modifier.weight(1f)) }
        )
    }
}