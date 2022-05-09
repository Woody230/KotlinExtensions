package com.bselzer.ktx.compose.ui.layout.snackbar

import androidx.compose.material.SnackbarData
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class SnackbarLogic(
    /**
     * Data about the current snackbar showing via SnackbarHostState
     */
    val data: SnackbarData,
) : LogicModel