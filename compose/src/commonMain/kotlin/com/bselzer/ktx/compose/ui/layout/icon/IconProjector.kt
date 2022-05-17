package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class IconProjector(
    override val interactor: IconInteractor,
    override val presenter: IconPresenter = IconPresenter.Default
) : Projector<IconInteractor, IconPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        Icon(
            painter = interactor.painter,
            contentDescription = interactor.contentDescription,
            modifier = combinedModifier,
            tint = tint
        )
    }
}