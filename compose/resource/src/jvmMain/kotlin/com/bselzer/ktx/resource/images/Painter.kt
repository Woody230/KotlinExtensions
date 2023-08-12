package com.bselzer.ktx.resource.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.ImageResource

@Composable
actual fun ImageResource.painter(): Painter = BitmapPainter(resourcesClassLoader.createBitmap(filePath))