package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BoxProjector(
    override val interactor: BoxInteractor = BoxInteractor.Default,
    override val presenter: BoxPresenter = BoxPresenter.Default
) : Projector<BoxInteractor, BoxPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        Box(
            modifier = combinedModifier,
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints.toBoolean(),
            content = content
        )
    }
}