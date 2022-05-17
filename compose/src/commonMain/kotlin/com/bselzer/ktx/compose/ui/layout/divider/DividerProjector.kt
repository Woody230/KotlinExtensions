package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DividerProjector(
    interactor: DividerInteractor = DividerInteractor.Default,
    presenter: DividerPresenter = DividerPresenter.Default
) : Projector<DividerInteractor, DividerPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Divider(
            modifier = combinedModifier,
            color = presenter.color,
            thickness = presenter.thickness,
            startIndent = presenter.startIndent
        )
    }
}