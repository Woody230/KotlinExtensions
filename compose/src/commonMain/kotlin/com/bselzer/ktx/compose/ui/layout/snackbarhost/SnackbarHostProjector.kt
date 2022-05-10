package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarInteractor
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarProjector

data class SnackbarHostProjector(
    override val interactor: SnackbarHostInteractor,
    override val presenter: SnackbarHostPresenter = SnackbarHostPresenter.Default
) : Projector<SnackbarHostInteractor, SnackbarHostPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        SnackbarHost(
            hostState = interactor.state,
            modifier = combinedModifier,
        ) { data ->
            SnackbarProjector(
                interactor = SnackbarInteractor(data = data),
                presenter = snackbar
            ).project()
        }
    }
}