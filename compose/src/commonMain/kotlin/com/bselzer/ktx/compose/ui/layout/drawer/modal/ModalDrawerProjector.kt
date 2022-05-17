package com.bselzer.ktx.compose.ui.layout.drawer.modal

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ModalDrawerProjector(
    override val interactor: ModalDrawerInteractor = ModalDrawerInteractor.Default,
    override val presenter: ModalDrawerPresenter = ModalDrawerPresenter.Default
) : Projector<ModalDrawerInteractor, ModalDrawerPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        drawerContent: @Composable ColumnScope.() -> Unit,
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        ModalDrawer(
            modifier = combinedModifier,
            drawerState = rememberSaveable(saver = DrawerState.Saver(interactor.confirmStateChange)) { interactor.state },
            gesturesEnabled = interactor.gesturesEnabled,
            drawerShape = shape,
            drawerElevation = elevation,
            drawerBackgroundColor = backgroundColor,
            drawerContentColor = contentColor,
            scrimColor = scrimColor,
            content = content,
            drawerContent = drawerContent
        )
    }
}