package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DividerProjection(
    override val logic: DividerLogic = DividerLogic(),
    override val presentation: DividerPresentation = DividerPresentation()
) : Projector<DividerLogic, DividerPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        Divider(
            modifier = modifier,
            color = color,
            thickness = thickness,
            startIndent = startIndent
        )
    }
}