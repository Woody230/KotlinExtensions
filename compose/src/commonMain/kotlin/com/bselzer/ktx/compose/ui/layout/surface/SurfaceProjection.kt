package com.bselzer.ktx.compose.ui.layout.surface

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SurfaceProjection(
    override val logic: SurfaceLogic = SurfaceLogic(),
    override val presentation: SurfacePresentation = SurfacePresentation()
) : Projector<SurfaceLogic, SurfacePresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) = contextualize {
        if (logic.onClick == null) {
            WithoutClick(modifier, content)
        } else {
            WithClick(modifier, interactionSource, logic.onClick, content)
        }
    }

    @Composable
    fun SurfacePresentation.WithoutClick(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) = Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        content = content
    )

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SurfacePresentation.WithClick(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        content: @Composable () -> Unit
    ) = Surface(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        interactionSource = interactionSource,
        indication = indication,
        enabled = logic.enabled,
        onClickLabel = logic.onClickLabel,
        role = role,
        content = content
    )
}