package com.bselzer.ktx.compose.ui.layout.modaldrawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjection
import com.bselzer.ktx.compose.ui.layout.description.DescriptionProjection
import com.bselzer.ktx.compose.ui.layout.icontext.IconTextProjection
import com.bselzer.ktx.compose.ui.layout.image.ImageProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ModalDrawerProjection(
    override val logic: ModalDrawerLogic,
    override val presentation: ModalDrawerPresentation = ModalDrawerPresentation.Default
) : Projector<ModalDrawerLogic, ModalDrawerPresentation>() {
    private val containerProjection = ColumnProjection(logic.container, presentation.container)
    private val imageProjection = logic.image?.let { image -> ImageProjection(image, presentation.image) }
    private val descriptionProjection = logic.description?.let { description -> DescriptionProjection(description, presentation.description) }
    private val componentProjections = logic.components.map { components -> components.map { component -> IconTextProjection(component, presentation.component) } }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) = contextualize {
        ModalDrawer(
            modifier = modifier,
            drawerState = rememberSaveable(saver = DrawerState.Saver(logic.confirmStateChange)) { logic.state },
            gesturesEnabled = logic.gesturesEnabled,
            drawerShape = shape,
            drawerElevation = elevation,
            drawerBackgroundColor = backgroundColor,
            drawerContentColor = contentColor,
            scrimColor = scrimColor,
            content = content,
            drawerContent = {
                containerProjection.project(
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                    content = arrayOf(header()).plus(componentProjections.map { it.section() })
                )
            }
        )
    }

    @Composable
    private fun header(): @Composable ColumnScope.() -> Unit = {
        Column(modifier = Modifier.fillMaxWidth()) {
            imageProjection?.project()
            descriptionProjection?.project()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    @Composable
    private fun List<IconTextProjection>.section(): @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            forEach { component -> component.project() }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}