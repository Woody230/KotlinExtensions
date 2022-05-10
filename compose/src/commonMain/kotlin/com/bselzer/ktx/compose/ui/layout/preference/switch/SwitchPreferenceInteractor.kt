package com.bselzer.ktx.compose.ui.layout.preference.switch

import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.switch.SwitchInteractor

data class SwitchPreferenceInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,
    val preference: PreferenceInteractor,
    val switch: SwitchInteractor,
) : Interactor(modifiers)