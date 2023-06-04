package com.bselzer.ktx.compose.ui.layout.preference.switch

import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.switch.SwitchInteractor

data class SwitchPreferenceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val preference: PreferenceInteractor,
    val switch: SwitchInteractor,
) : Interactor(modifier)