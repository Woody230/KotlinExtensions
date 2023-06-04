package com.bselzer.ktx.compose.ui.layout.preference.switch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.switch.SwitchProjector

class SwitchPreferenceProjector(
    interactor: SwitchPreferenceInteractor,
    presenter: SwitchPreferencePresenter = SwitchPreferencePresenter.Default
) : Projector<SwitchPreferenceInteractor, SwitchPreferencePresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val preferenceProjector = PreferenceProjector(interactor.preference, presenter.preference)
        val switchProjector = SwitchProjector(interactor.switch, presenter.switch)

        preferenceProjector.Projection(combinedModifier.clickable(
            // If the container is clicked then mimic a click on the switch.
            // TODO is there some way to programmatically click the switch instead of doing the logic here as well?
            //  interaction source emitting only affects the indication and does not perform the handler
            enabled = interactor.switch.enabled,
            onClick = {
                if (interactor.switch.enabled) {
                    interactor.switch.onCheckedChange?.let { onCheckedChange ->
                        onCheckedChange(!interactor.switch.checked)
                    }
                }
            }
        )) {
            switchProjector.Projection(interactionSource = interactionSource)
        }
    }
}