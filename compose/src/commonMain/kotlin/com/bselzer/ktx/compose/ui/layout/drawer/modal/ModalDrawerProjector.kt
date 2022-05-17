package com.bselzer.ktx.compose.ui.layout.drawer.modal

import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.column.addIfNotNull
import com.bselzer.ktx.compose.ui.layout.drawer.header.DrawerHeaderProjector
import com.bselzer.ktx.compose.ui.layout.drawer.section.DrawerSectionProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.function.collection.buildArray

class ModalDrawerProjector(
    interactor: ModalDrawerInteractor = ModalDrawerInteractor.Default,
    presenter: ModalDrawerPresenter = ModalDrawerPresenter.Default
) : Projector<ModalDrawerInteractor, ModalDrawerPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        ModalDrawer(
            modifier = combinedModifier,
            drawerState = rememberSaveable(saver = DrawerState.Saver(interactor.confirmStateChange)) { interactor.state },
            gesturesEnabled = interactor.gesturesEnabled,
            drawerShape = presenter.shape,
            drawerElevation = presenter.elevation,
            drawerBackgroundColor = presenter.backgroundColor,
            drawerContentColor = presenter.contentColor,
            scrimColor = presenter.scrimColor,
            content = content,
            drawerContent = {
                // Will need to contextualize again so don't pass the current combined modifier to avoid duplication.
                DrawerContent()
            }
        )
    }

    @Composable
    fun DrawerContent(modifier: Modifier = Modifier) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val containerProjector = ColumnProjector(interactor.container, presenter.container)
        val headerProjector = interactor.header?.let { DrawerHeaderProjector(it, presenter.header) }
        val sectionProjectors = interactor.sections.map { DrawerSectionProjector(it, presenter.section) }

        containerProjector.Projection(
            modifier = combinedModifier,
            content = buildArray {
                addIfNotNull(headerProjector) { it.Projection() }
                sectionProjectors.forEach { section ->
                    add { section.Projection() }
                }
            }
        )
    }
}