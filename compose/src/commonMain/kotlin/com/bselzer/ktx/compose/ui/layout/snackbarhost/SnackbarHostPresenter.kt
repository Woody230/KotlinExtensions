package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.snackbar.SnackbarPresenter

data class SnackbarHostPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presentable] for the snackbar.
     */
    val snackbar: SnackbarPresenter = SnackbarPresenter.Default
) : Presenter<SnackbarHostPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = SnackbarHostPresenter()
    }

    override fun safeMerge(other: SnackbarHostPresenter) = SnackbarHostPresenter(
        modifiers = modifiers.merge(other.modifiers),
        snackbar = snackbar.merge(other.snackbar)
    )

    @Composable
    override fun localized() = copy(snackbar = snackbar.localized())
}