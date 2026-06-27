package com.bselzer.ktx.compose.resource.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import dev.icerock.moko.resources.ImageResource

@Composable
actual fun ImageResource.painter(): Painter = painterResource(id = drawableResId)