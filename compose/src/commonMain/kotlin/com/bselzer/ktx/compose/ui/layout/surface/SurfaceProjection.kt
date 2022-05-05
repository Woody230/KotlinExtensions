package com.bselzer.ktx.compose.ui.layout.surface

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class SurfaceProjection(
    override val logic: SurfaceLogic = SurfaceLogic(),
    override val presentation: SurfacePresentation = SurfacePresentation()
) : Projectable<SurfaceLogic, SurfacePresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) {
        val shape = ComposeMerger.shape.resolve(presentation.shape, RectangleShape)
        val color = ComposeMerger.color.resolve(presentation.color, MaterialTheme.colors.surface)
        val contentColor = ComposeMerger.color.resolve(presentation.contentColor, contentColorFor(color))

        if (logic.onClick == null) {
            WithoutClick(modifier, shape, color, contentColor, content)
        } else {
            WithClick(modifier, interactionSource, logic.onClick, shape, color, contentColor, content)
        }
    }

    @Composable
    fun WithoutClick(
        modifier: Modifier,
        shape: Shape,
        color: Color,
        contentColor: Color,
        content: @Composable () -> Unit
    ) = Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = presentation.border,
        elevation = presentation.elevation,
        content = content
    )

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun WithClick(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        shape: Shape,
        color: Color,
        contentColor: Color,
        content: @Composable () -> Unit
    ) = Surface(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = presentation.border,
        elevation = presentation.elevation,
        interactionSource = interactionSource,
        indication = presentation.indication,
        enabled = logic.enabled,
        onClickLabel = logic.onClickLabel,
        role = presentation.role,
        content = content
    )
}