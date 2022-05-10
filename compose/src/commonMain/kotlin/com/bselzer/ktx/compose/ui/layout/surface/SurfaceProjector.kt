package com.bselzer.ktx.compose.ui.layout.surface

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SurfaceProjector(
    override val interactor: SurfaceInteractor = SurfaceInteractor.Default,
    override val presenter: SurfacePresenter = SurfacePresenter.Default
) : Projector<SurfaceInteractor, SurfacePresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) = contextualize {
        if (interactor.onClick == null) {
            WithoutClick(modifier, content)
        } else {
            WithClick(modifier, interactionSource, interactor.onClick, content)
        }
    }

    @Composable
    fun SurfacePresenter.WithoutClick(
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
    fun SurfacePresenter.WithClick(
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
        enabled = interactor.enabled,
        onClickLabel = interactor.onClickLabel,
        role = role,
        content = content
    )
}