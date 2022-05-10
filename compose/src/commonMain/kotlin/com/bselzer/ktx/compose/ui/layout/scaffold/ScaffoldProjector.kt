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
    override val interactor: ScaffoldInteractor,
    override val presenter: ScaffoldPresenter = ScaffoldPresenter.Default
) : Projector<ScaffoldInteractor, ScaffoldPresenter>() {
    private val drawerProjection = ModalDrawerProjector(interactor.drawer, presenter.drawer)
    private val snackbarHostProjection = SnackbarHostProjector(interactor.snackbarHost, presenter.snackbarHost)
    private val topBarProjection = interactor.topBar?.let { bar -> TopAppBarProjector(bar, presenter.topBar) }
    private val bottomBarProjection = interactor.bottomBar?.let { bar -> BottomAppBarProjector(bar, presenter.bottomBar) }
    private val fabProjection = interactor.floatingActionButton?.let { fab -> FloatingActionButtonProjector(fab, presenter.floatingActionButton) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable (PaddingValues) -> Unit
    ) = contextualize {
        Scaffold(
            modifier = modifier,
            scaffoldState = remember { interactor.state },
            topBar = { topBarProjection?.project() },
            bottomBar = { bottomBarProjection?.project() },
            snackbarHost = { snackbarHostProjection.project() },
            floatingActionButton = { fabProjection?.project() },
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked.toBoolean(),
            drawerContent = { drawerProjection.drawerContent() },
            drawerGesturesEnabled = interactor.drawer.gesturesEnabled,
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