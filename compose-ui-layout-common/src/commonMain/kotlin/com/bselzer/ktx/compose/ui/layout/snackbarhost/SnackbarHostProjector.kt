package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarInteractor
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarProjector

class SnackbarHostProjector(
    interactor: SnackbarHostInteractor = SnackbarHostInteractor.Default,
    presenter: SnackbarHostPresenter = SnackbarHostPresenter.Default
) : Projector<SnackbarHostInteractor, SnackbarHostPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        SnackbarHost(
            hostState = interactor.state,
            modifier = combinedModifier,
        ) { data ->
            SnackbarProjector(
                interactor = SnackbarInteractor(data = data),
                presenter = presenter.snackbar
            ).Projection()
        }
    }
}