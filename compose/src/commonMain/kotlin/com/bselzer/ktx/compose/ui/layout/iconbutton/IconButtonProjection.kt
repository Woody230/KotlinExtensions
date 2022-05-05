package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class IconButtonProjection(
    override val logic: IconButtonLogic,
    override val presentation: IconButtonPresentation = IconButtonPresentation()
) : Projectable<IconButtonLogic, IconButtonPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) = IconButton(
        onClick = logic.onClick,
        modifier = modifier,
        enabled = logic.enabled,
        interactionSource = interactionSource,
        content = content
    )
}
