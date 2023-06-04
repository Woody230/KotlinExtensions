package com.bselzer.ktx.resource.images

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.io.FileNotFoundException

/**
 * Creates the bitmap associated with resource at the given [filePath].
 */
internal fun ClassLoader.createBitmap(filePath: String): ImageBitmap {
    val stream = getResourceAsStream(filePath) ?: throw FileNotFoundException("Couldn't open resource as stream at: $filePath")
    stream.use {
        val bytes = it.readBytes()
        return Image.makeFromEncoded(bytes).toComposeImageBitmap()
    }
}
