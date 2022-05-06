package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ImageProjection(
    override val logic: ImageLogic,
    override val presentation: ImagePresentation = ImagePresentation()
) : Projector<ImageLogic, ImagePresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        Image(
            painter = logic.painter,
            contentDescription = logic.contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}