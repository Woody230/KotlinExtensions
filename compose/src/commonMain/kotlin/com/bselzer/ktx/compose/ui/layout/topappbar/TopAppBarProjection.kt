package com.bselzer.ktx.compose.ui.layout.topappbar

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjection

class TopAppBarProjection(
    override val logic: TopAppBarLogic,
    override val presentation: TopAppBarPresentation
) : Projector<TopAppBarLogic, TopAppBarPresentation>() {
    private val titleProjection = TextProjection(logic.title, presentation.title)
    private val navigationProjection = logic.navigation?.let { navigation -> IconButtonProjection(navigation, presentation.icon) }
    private val actionProjection = logic.actions.map { action -> IconButtonProjection(action, presentation.icon) }

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        TopAppBar(
            title = { titleProjection.project() },
            modifier = modifier,
            navigationIcon = navigationProjection?.let { navigation -> { navigation.project() } },
            actions = { actionProjection.forEach { action -> action.project() } },
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation
        )
    }
}