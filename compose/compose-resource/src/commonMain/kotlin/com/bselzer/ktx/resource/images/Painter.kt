package com.bselzer.ktx.resource.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.ImageResource

/**
 * Converts the [ImageResource] into a [Painter].
 */
@Composable
expect fun ImageResource.painter(): Painter