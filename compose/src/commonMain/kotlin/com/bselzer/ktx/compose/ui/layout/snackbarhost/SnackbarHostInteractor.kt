package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHostState
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SnackbarHostInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * Component to host Snackbars that are pushed to be shown via SnackbarHostState.showSnackbar. Usually it's a SnackbarHost
     */
    val state: SnackbarHostState,
) : Interactor(modifiers)