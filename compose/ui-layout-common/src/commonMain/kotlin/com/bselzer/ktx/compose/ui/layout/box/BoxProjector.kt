package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BoxProjector(
    interactor: BoxInteractor = BoxInteractor.Default,
    presenter: BoxPresenter = BoxPresenter.Default
) : Projector<BoxInteractor, BoxPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Box(
            modifier = combinedModifier,
            contentAlignment = presenter.contentAlignment,
            propagateMinConstraints = presenter.propagateMinConstraints.toBoolean(),
            content = content
        )
    }
}