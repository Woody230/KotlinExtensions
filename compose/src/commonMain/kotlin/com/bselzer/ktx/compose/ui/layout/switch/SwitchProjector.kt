package com.bselzer.ktx.compose.ui.layout.switch

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SwitchProjector(
    override val interactor: SwitchInteractor,
    override val presenter: SwitchPresenter = SwitchPresenter.Default
) : Projector<SwitchInteractor, SwitchPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier ->
        Switch(
            checked = interactor.checked,
            onCheckedChange = interactor.onCheckedChange,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
            colors = colors
        )
    }
}