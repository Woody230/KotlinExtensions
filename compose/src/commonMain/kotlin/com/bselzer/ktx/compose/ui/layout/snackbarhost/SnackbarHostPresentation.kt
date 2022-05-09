package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarPresentation

data class SnackbarHostPresentation(
    /**
     * The [PresentationModel] for the snackbar.
     */
    val snackbar: SnackbarPresentation = SnackbarPresentation.Default
) : Presenter<SnackbarHostPresentation>() {
    companion object {
        @Stable
        val Default = SnackbarHostPresentation()
    }

    override fun safeMerge(other: SnackbarHostPresentation) = SnackbarHostPresentation(
        snackbar = snackbar.merge(other.snackbar)
    )

    @Composable
    override fun localized() = copy(snackbar = snackbar.localized())
}