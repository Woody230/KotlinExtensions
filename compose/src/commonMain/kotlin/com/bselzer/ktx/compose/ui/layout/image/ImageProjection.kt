package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class ImageProjection(
    override val logic: ImageLogic,
    override val presentation: ImagePresentation = ImagePresentation()
) : Projectable<ImageLogic, ImagePresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = Image(
        painter = logic.painter,
        contentDescription = logic.contentDescription,
        modifier = modifier,
        alignment = presentation.alignment,
        contentScale = presentation.contentScale,
        alpha = presentation.alpha,
        colorFilter = presentation.colorFilter
    )
}