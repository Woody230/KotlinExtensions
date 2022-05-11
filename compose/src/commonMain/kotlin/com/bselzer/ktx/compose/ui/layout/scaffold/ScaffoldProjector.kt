package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarProjector
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonProjector
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostProjector
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarProjector

class ScaffoldProjector(
    override val interactor: ScaffoldInteractor = ScaffoldInteractor.Default,
    override val presenter: ScaffoldPresenter = ScaffoldPresenter.Default
) : Projector<ScaffoldInteractor, ScaffoldPresenter>() {
    private val drawerProjector = interactor.drawer?.let { drawer -> ModalDrawerProjector(drawer, presenter.drawer) }
    private val snackbarHostProjector = SnackbarHostProjector(interactor.snackbarHost, presenter.snackbarHost)
    private val topBarProjector = interactor.topBar?.let { bar -> TopAppBarProjector(bar, presenter.topBar) }
    private val bottomBarProjector = interactor.bottomBar?.let { bar -> BottomAppBarProjector(bar, presenter.bottomBar) }
    private val fabProjector = interactor.floatingActionButton?.let { fab -> FloatingActionButtonProjector(fab, presenter.floatingActionButton) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable (PaddingValues) -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        Scaffold(
            modifier = combinedModifier,
            scaffoldState = remember { interactor.state },
            topBar = { topBarProjector?.Projection() },
            bottomBar = { bottomBarProjector?.Projection() },
            snackbarHost = { snackbarHostProjector.Projection() },
            floatingActionButton = { fabProjector?.Projection() },
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked.toBoolean(),
            drawerContent = drawerProjector?.let { drawer -> { drawer.drawerContent() } },
            drawerGesturesEnabled = interactor.drawer?.gesturesEnabled ?: true,
            drawerShape = drawer.shape,
            drawerElevation = drawer.elevation,
            drawerBackgroundColor = drawer.backgroundColor,
            drawerContentColor = drawer.contentColor,
            drawerScrimColor = drawer.scrimColor,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            content = content
        )
    }
}