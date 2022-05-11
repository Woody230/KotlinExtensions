package com.bselzer.ktx.compose.ui.layout.icontext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.row.RowProjector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class IconTextProjector(
    override val interactor: IconTextInteractor,
    override val presenter: IconTextPresenter = IconTextPresenter.Default
) : Projector<IconTextInteractor, IconTextPresenter>() {
    private val containerProjector = RowProjector(interactor.container, presenter.container)
    private val iconProjector = IconProjector(interactor.icon, presenter.icon)
    private val textProjector = TextProjector(interactor.text, presenter.text)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        containerProjector.Projection(
            modifier = combinedModifier,
            { iconProjector.Projection() },
            { textProjector.Projection(modifier = Modifier.weight(1f)) }
        )
    }
}