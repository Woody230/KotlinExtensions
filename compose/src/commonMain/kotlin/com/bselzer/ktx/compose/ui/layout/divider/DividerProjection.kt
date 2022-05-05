package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class DividerProjection(
    override val logic: DividerLogic = DividerLogic(),
    override val presentation: DividerPresentation = DividerPresentation()
) : Projectable<DividerLogic, DividerPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = Divider(
        modifier = modifier,
        color = ComposeMerger.color.safeTake(presentation.color, MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
        thickness = presentation.thickness,
        startIndent = presentation.startIndent
    )
}