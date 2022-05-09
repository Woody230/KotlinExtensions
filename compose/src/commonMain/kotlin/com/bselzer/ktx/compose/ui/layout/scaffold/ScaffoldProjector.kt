package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarProjection
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonProjection
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostProjection
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarProjection

class ScaffoldProjector(
    override val logic: ScaffoldLogic,
    override val presentation: ScaffoldPresentation = ScaffoldPresentation.Default
) : Projector<ScaffoldLogic, ScaffoldPresentation>() {
    private val drawerProjection = ModalDrawerProjection(logic.drawer, presentation.drawer)
    private val snackbarHostProjection = SnackbarHostProjection(logic.snackbarHost, presentation.snackbarHost)
    private val topBarProjection = logic.topBar?.let { bar -> TopAppBarProjection(bar, presentation.topBar) }
    private val bottomBarProjection = logic.bottomBar?.let { bar -> BottomAppBarProjection(bar, presentation.bottomBar) }
    private val fabProjection = logic.floatingActionButton?.let { fab -> FloatingActionButtonProjection(fab, presentation.floatingActionButton) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable (PaddingValues) -> Unit
    ) = contextualize {
        Scaffold(
            modifier = modifier,
            scaffoldState = remember { logic.state },
            topBar = { topBarProjection?.project() },
            bottomBar = { bottomBarProjection?.project() },
            snackbarHost = { snackbarHostProjection.project() },
            floatingActionButton = { fabProjection?.project() },
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked.toBoolean(),
            drawerContent = { drawerProjection.drawerContent() },
            drawerGesturesEnabled = logic.drawer.gesturesEnabled,
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