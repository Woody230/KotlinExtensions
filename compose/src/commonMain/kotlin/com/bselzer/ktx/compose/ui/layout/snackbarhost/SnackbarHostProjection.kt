package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarLogic
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarProjection

data class SnackbarHostProjection(
    override val logic: SnackbarHostLogic,
    override val presentation: SnackbarHostPresentation = SnackbarHostPresentation.Default
) : Projector<SnackbarHostLogic, SnackbarHostPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        SnackbarHost(
            hostState = logic.state,
            modifier = modifier,
        ) { data ->
            SnackbarProjection(
                logic = SnackbarLogic(data = data),
                presentation = snackbar
            ).project()
        }
    }
}