package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DividerProjector(
    override val interactor: DividerInteractor = DividerInteractor.Default,
    override val presenter: DividerPresenter = DividerPresenter.Default
) : Projector<DividerInteractor, DividerPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        Divider(
            modifier = combinedModifier,
            color = color,
            thickness = thickness,
            startIndent = startIndent
        )
    }
}