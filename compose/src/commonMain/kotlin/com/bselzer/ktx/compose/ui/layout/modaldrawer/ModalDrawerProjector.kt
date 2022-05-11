package com.bselzer.ktx.compose.ui.layout.modaldrawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.description.DescriptionProjector
import com.bselzer.ktx.compose.ui.layout.icontext.IconTextProjector
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ModalDrawerProjector(
    override val interactor: ModalDrawerInteractor = ModalDrawerInteractor.Default,
    override val presenter: ModalDrawerPresenter = ModalDrawerPresenter.Default
) : Projector<ModalDrawerInteractor, ModalDrawerPresenter>() {
    private val containerProjector = ColumnProjector(interactor.container, presenter.container)
    private val imageProjector = interactor.image?.let { image -> ImageProjector(image, presenter.image) }
    private val descriptionProjector = interactor.description?.let { description -> DescriptionProjector(description, presenter.description) }
    private val componentProjectors = interactor.components.map { components -> components.map { component -> IconTextProjector(component, presenter.component) } }

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
            drawerContent = { drawerContent() }
        )
    }

    @Composable
    fun drawerContent() = containerProjector.Projection(
        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
        content = arrayOf(header()).plus(componentProjectors.map { it.section() })
    )

    @Composable
    private fun header(): @Composable ColumnScope.() -> Unit = {
        Column(modifier = Modifier.fillMaxWidth()) {
            imageProjector?.Projection()
            descriptionProjector?.Projection()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    @Composable
    private fun List<IconTextProjector>.section(): @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            forEach { component -> component.Projection() }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}