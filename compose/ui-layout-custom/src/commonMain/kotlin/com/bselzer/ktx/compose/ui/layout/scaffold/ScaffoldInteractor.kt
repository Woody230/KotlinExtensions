package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.appbar.bottom.BottomAppBarInteractor
import com.bselzer.ktx.compose.ui.layout.appbar.top.TopAppBarInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.LocalDrawerState
import com.bselzer.ktx.compose.ui.layout.drawer.modal.ModalDrawerInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.modal.rememberState
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostInteractor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.rememberState

data class ScaffoldInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the drawer.
     */
    val drawer: ModalDrawerInteractor? = null,

    /**
     * The [Interactor] for the snackbar host.
     */
    val snackbarHost: SnackbarHostInteractor = SnackbarHostInteractor.Default,

    /**
     * The [Interactor] for the top app bar.
     */
    val topBar: TopAppBarInteractor? = null,

    /**
     * The [Interactor] for the bottom app bar.
     */
    val bottomBar: BottomAppBarInteractor? = null,

    /**
     * The [Interactor] for the floating action button.
     */
    val floatingActionButton: FloatingActionButtonInteractor? = null,

    /**
     * The window insets to be passed to content slot via PaddingValues params. Scaffold will take the insets into account from the top/bottom only if the topBar/ bottomBar are not present, as the scaffold expect topBar/bottomBar to handle insets instead. Any insets consumed by other insets padding modifiers or consumeWindowInsets on a parent layout will be excluded from contentWindowInsets.
     */
    val contentWindowInsets: WindowInsets? = null
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = ScaffoldInteractor()
    }
}

/**
 * Creates a [ScaffoldInteractor] from the [drawer] and [snackbarHost].
 *
 * The states of the [drawer] and [snackbarHost] are remembered and provided to the [LocalDrawerState] and [LocalSnackbarHostState].
 */
@Composable
fun scaffoldInteractor(
    drawer: ModalDrawerInteractor,
    snackbarHost: SnackbarHostInteractor,
    content: @Composable (ScaffoldInteractor) -> Unit
) = drawer.rememberState {
    snackbarHost.rememberState {
        val interactor = ScaffoldInteractor(drawer = drawer, snackbarHost = snackbarHost)
        content(interactor)
    }
}