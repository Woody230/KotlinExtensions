package com.bselzer.ktx.compose.resource.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import dev.icerock.moko.resources.ImageResource
import org.jetbrains.skia.Image
import java.io.FileNotFoundException

@Composable
actual fun ImageResource.painter(): Painter {
    val stream = resourcesClassLoader.getResourceAsStream(filePath) ?: throw FileNotFoundException("Couldn't open resource as stream at: $filePath")
    stream.use {
        val bytes = it.readBytes()
        return BitmapPainter(image = Image.makeFromEncoded(bytes).toComposeImageBitmap())
    }
}