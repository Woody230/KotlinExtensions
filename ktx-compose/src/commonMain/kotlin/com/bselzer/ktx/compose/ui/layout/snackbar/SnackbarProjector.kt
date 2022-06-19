package com.bselzer.ktx.compose.ui.layout.snackbar

import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SnackbarProjector(
    interactor: SnackbarInteractor,
    presenter: SnackbarPresenter = SnackbarPresenter.Default
) : Projector<SnackbarInteractor, SnackbarPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Snackbar(
            snackbarData = interactor.data,
            modifier = combinedModifier,
            actionOnNewLine = presenter.actionOnNewLine.toBoolean(),
            shape = presenter.shape,
            backgroundColor = presenter.backgroundColor,
            contentColor = presenter.contentColor,
            actionColor = presenter.actionColor,
            elevation = presenter.elevation
        )
    }
}
