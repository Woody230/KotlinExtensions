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
    private val containerProjection = RowProjector(interactor.container, presenter.container)
    private val iconProjection = IconProjector(interactor.icon, presenter.icon)
    private val textProjection = TextProjector(interactor.text, presenter.text)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = containerProjection.project(
        modifier = modifier,
        { iconProjection.project() },
        { textProjection.project(modifier = Modifier.weight(1f)) }
    )
}