package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class IconProjection(
    override val logic: IconLogic,
    override val presentation: IconPresentation = IconPresentation()
) : Projector<IconLogic, IconPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        Icon(
            imageVector = logic.imageVector,
            contentDescription = logic.contentDescription,
            modifier = modifier,
            tint = tint
        )
    }
}