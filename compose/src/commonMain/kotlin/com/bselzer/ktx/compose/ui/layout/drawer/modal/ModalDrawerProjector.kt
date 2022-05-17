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
    override val interactor: ModalDrawerInteractor = ModalDrawerInteractor.Default,
    override val presenter: ModalDrawerPresenter = ModalDrawerPresenter.Default
) : Projector<ModalDrawerInteractor, ModalDrawerPresenter>() {
    private val containerProjector = ColumnProjector(interactor.container, presenter.container)
    private val headerProjector = interactor.header?.let { header -> DrawerHeaderProjector(header, presenter.header) }
    private val sectionProjectors = interactor.sections.map { section -> DrawerSectionProjector(section, presenter.section) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
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
            drawerContent = { DrawerContent(combinedModifier) }
        )
    }

    @Composable
    fun DrawerContent(modifier: Modifier = Modifier) = containerProjector.Projection(
        modifier = modifier,
        content = buildArray {
            addIfNotNull(headerProjector) { it.Projection() }
            sectionProjectors.forEach { section ->
                add { section.Projection() }
            }
        }
    )
}