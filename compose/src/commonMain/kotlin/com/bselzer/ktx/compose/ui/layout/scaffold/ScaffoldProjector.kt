package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.appbar.bottom.BottomAppBarProjector
import com.bselzer.ktx.compose.ui.layout.appbar.top.TopAppBarProjector
import com.bselzer.ktx.compose.ui.layout.drawer.LocalDrawerState
import com.bselzer.ktx.compose.ui.layout.drawer.modal.ModalDrawerInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.modal.ModalDrawerProjector
import com.bselzer.ktx.compose.ui.layout.drawer.modal.rememberState
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostInteractor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostProjector
import com.bselzer.ktx.compose.ui.layout.snackbarhost.rememberState
import com.bselzer.ktx.compose.ui.notification.snackbar.LocalSnackbarHostState

class ScaffoldProjector(
    interactor: ScaffoldInteractor = ScaffoldInteractor.Default,
    presenter: ScaffoldPresenter = ScaffoldPresenter.Default
) : Projector<ScaffoldInteractor, ScaffoldPresenter>(interactor, presenter) {
    /**
     * Projects the scaffold into its components: a modal drawer, a snackbar host, a top app bar, a bottom app bar, and a floating action button.
     *
     * If using the modal drawer, then its state should be persisted through the [LocalDrawerState] via [ModalDrawerInteractor.rememberState].
     *
     * If using the snackbar host, then its state should be persisted through the [LocalSnackbarHostState] via [SnackbarHostInteractor.rememberState].
     *
     * These states can alternatively be persisted through [scaffoldInteractor].
     */
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable (PaddingValues) -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val drawerProjector = interactor.drawer?.let { drawer -> ModalDrawerProjector(drawer, presenter.drawer) }
        val topBarProjector = interactor.topBar?.let { bar -> TopAppBarProjector(bar, presenter.topBar) }
        val bottomBarProjector = interactor.bottomBar?.let { bar -> BottomAppBarProjector(bar, presenter.bottomBar) }
        val fabProjector = interactor.floatingActionButton?.let { fab -> FloatingActionButtonProjector(fab, presenter.floatingActionButton) }

        // The scaffold controls displaying the drawer and so we cannot project the whole drawer, just its content.
        // Consequently, we must provide the drawer components explicitly by localizing the presenter right now.
        val drawer = presenter.drawer.localized()
        Scaffold(
            modifier = combinedModifier,
            scaffoldState = rememberScaffoldState(
                drawerState = interactor.drawer?.state ?: ModalDrawerInteractor.Default.state,
                snackbarHostState = interactor.snackbarHost.state
            ),
            topBar = { topBarProjector?.Projection() },
            bottomBar = { bottomBarProjector?.Projection() },
            snackbarHost = { hostState ->
                SnackbarHostProjector(
                    interactor = interactor.snackbarHost.copy(state = hostState),
                    presenter.snackbarHost
                ).Projection()
            },
            floatingActionButton = { fabProjector?.Projection() },
            floatingActionButtonPosition = presenter.floatingActionButtonPosition,
            isFloatingActionButtonDocked = presenter.isFloatingActionButtonDocked.toBoolean(),
            drawerContent = drawerProjector?.let { { it.DrawerContent() } },
            drawerGesturesEnabled = interactor.drawer?.gesturesEnabled ?: true,
            drawerShape = drawer.shape,
            drawerElevation = drawer.elevation,
            drawerBackgroundColor = drawer.backgroundColor,
            drawerContentColor = drawer.contentColor,
            drawerScrimColor = drawer.scrimColor,
            backgroundColor = presenter.backgroundColor,
            contentColor = presenter.contentColor,
            content = content
        )
    }
}