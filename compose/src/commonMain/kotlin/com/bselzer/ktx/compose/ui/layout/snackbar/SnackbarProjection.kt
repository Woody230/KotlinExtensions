package com.bselzer.ktx.compose.ui.layout.snackbar

import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

data class SnackbarProjection(
    override val logic: SnackbarLogic,
    override val presentation: SnackbarPresentation = SnackbarPresentation.Default
) : Projector<SnackbarLogic, SnackbarPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
    ) = contextualize {
        Snackbar(
            snackbarData = logic.data,
            modifier = modifier,
            actionOnNewLine = actionOnNewLine.toBoolean(),
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            actionColor = actionColor,
            elevation = elevation
        )
    }
}
