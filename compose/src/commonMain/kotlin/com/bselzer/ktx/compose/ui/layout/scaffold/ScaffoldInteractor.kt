package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.LocalDrawerState
import com.bselzer.ktx.compose.ui.layout.drawer.modal.ModalDrawerInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.modal.rememberState
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostInteractor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.rememberState
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarInteractor
import com.bselzer.ktx.compose.ui.notification.snackbar.LocalSnackbarHostState

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