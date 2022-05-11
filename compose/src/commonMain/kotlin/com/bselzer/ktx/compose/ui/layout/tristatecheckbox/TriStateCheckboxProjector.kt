package com.bselzer.ktx.compose.ui.layout.tristatecheckbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class TriStateCheckboxProjector(
    override val interactor: TriStateCheckboxInteractor,
    override val presenter: TriStateCheckboxPresenter = TriStateCheckboxPresenter.Default
) : Projector<TriStateCheckboxInteractor, TriStateCheckboxPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier ->
        TriStateCheckbox(
            state = interactor.state,
            onClick = interactor.onClick,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
            colors = colors
        )
    }
}