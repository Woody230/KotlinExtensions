package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHostState
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class SnackbarHostLogic(
    /**
     * Component to host Snackbars that are pushed to be shown via SnackbarHostState.showSnackbar. Usually it's a SnackbarHost
     */
    val state: SnackbarHostState,
) : LogicModel