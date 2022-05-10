package com.bselzer.ktx.compose.ui.layout.snackbar

import androidx.compose.material.SnackbarData
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SnackbarInteractor(
    /**
     * Data about the current snackbar showing via SnackbarHostState
     */
    val data: SnackbarData,
) : Interactor()