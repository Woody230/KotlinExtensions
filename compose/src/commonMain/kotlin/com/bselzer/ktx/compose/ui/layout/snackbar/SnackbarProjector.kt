package com.bselzer.ktx.compose.ui.layout.snackbar

import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

data class SnackbarProjector(
    override val interactor: SnackbarInteractor,
    override val presenter: SnackbarPresenter = SnackbarPresenter.Default
) : Projector<SnackbarInteractor, SnackbarPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier ->
        Snackbar(
            snackbarData = interactor.data,
            modifier = combinedModifier,
            actionOnNewLine = actionOnNewLine.toBoolean(),
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            actionColor = actionColor,
            elevation = elevation
        )
    }
}
