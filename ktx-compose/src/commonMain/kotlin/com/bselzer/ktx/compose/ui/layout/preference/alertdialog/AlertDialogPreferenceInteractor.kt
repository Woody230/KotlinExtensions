package com.bselzer.ktx.compose.ui.layout.preference.alertdialog

import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class AlertDialogPreferenceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val preference: PreferenceInteractor,
    val dialog: AlertDialogInteractor,
) : Interactor(modifier)