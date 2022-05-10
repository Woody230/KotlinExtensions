package com.bselzer.ktx.compose.ui.layout.preference.checkbox

import com.bselzer.ktx.compose.ui.layout.checkbox.CheckboxInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class CheckboxPreferenceInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,
    val preference: PreferenceInteractor,
    val checkbox: CheckboxInteractor,
) : Interactor(modifiers)