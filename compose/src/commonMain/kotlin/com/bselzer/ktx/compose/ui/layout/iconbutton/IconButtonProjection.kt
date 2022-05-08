package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector

class IconButtonProjection(
    override val logic: IconButtonLogic,
    override val presentation: IconButtonPresentation = IconButtonPresentation.Default
) : Projector<IconButtonLogic, IconButtonPresentation>() {
    private val iconProjection = IconProjection(logic.icon, presentation.icon)

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize {
        IconButton(
            onClick = logic.onClick,
            modifier = modifier,
            enabled = logic.enabled,
            interactionSource = interactionSource,
        ) {
            iconProjection.project()
        }
    }
}
