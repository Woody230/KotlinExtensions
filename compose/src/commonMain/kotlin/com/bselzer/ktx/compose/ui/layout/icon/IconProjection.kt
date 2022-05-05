package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class IconProjection(
    override val logic: IconLogic,
    override val presentation: IconPresentation = IconPresentation()
) : Projectable<IconLogic, IconPresentation> {
    @Composable
    fun project(modifier: Modifier = Modifier) = Icon(
        imageVector = logic.imageVector,
        contentDescription = logic.contentDescription,
        modifier = modifier,
        tint = ComposeMerger.color.safeTake(presentation.tint, LocalContentColor.current.copy(alpha = LocalContentAlpha.current))
    )
}