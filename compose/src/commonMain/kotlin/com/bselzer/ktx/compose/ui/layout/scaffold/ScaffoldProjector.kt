package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.DrawerState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarProjector
import com.bselzer.ktx.compose.ui.layout.drawer.modal.ModalDrawerInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.modal.ModalDrawerProjector
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostProjector
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarProjector
import com.bselzer.ktx.compose.ui.notification.snackbar.LocalSnackbarHostState

class ScaffoldProjector(
    interactor: ScaffoldInteractor = ScaffoldInteractor.Default,
    presenter: ScaffoldPresenter = ScaffoldPresenter.Default
) : Projector<ScaffoldInteractor, ScaffoldPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable (PaddingValues) -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val drawerProjector = interactor.drawer?.let { drawer -> ModalDrawerProjector(drawer, presenter.drawer) }
        val snackbarHostProjector = SnackbarHostProjector(interactor.snackbarHost, presenter.snackbarHost)
        val topBarProjector = interactor.topBar?.let { bar -> TopAppBarProjector(bar, presenter.topBar) }
        val bottomBarProjector = interactor.bottomBar?.let { bar -> BottomAppBarProjector(bar, presenter.bottomBar) }
        val fabProjector = interactor.floatingActionButton?.let { fab -> FloatingActionButtonProjector(fab, presenter.floatingActionButton) }

        val snackbarHost = remember { interactor.snackbarHost.state }
        CompositionLocalProvider(
            LocalSnackbarHostState provides snackbarHost
        ) {
            // The scaffold controls displaying the drawer and so we cannot project the whole drawer, just its content.
            // Consequently, we must provide the drawer components explicitly by localizing the presenter right now.
            val drawer = presenter.drawer.localized()
            Scaffold(
                modifier = combinedModifier,
                scaffoldState = rememberScaffoldState(
                    drawerState = with(interactor.drawer ?: ModalDrawerInteractor.Default) {
                        rememberSaveable(saver = DrawerState.Saver(confirmStateChange)) { state }
                    },
                    snackbarHostState = snackbarHost
                ),
                topBar = { topBarProjector?.Projection() },
                bottomBar = { bottomBarProjector?.Projection() },
                snackbarHost = { snackbarHostProjector.Projection() },
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
}