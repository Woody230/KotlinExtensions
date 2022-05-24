package com.bselzer.ktx.compose.image.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntOffset
import com.bselzer.ktx.compose.image.model.Image

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
internal expect fun ByteArray.asImageBitmap(): ImageBitmap

/**
 * Creates a painter with the byte content from the [image].
 *
 * @param image the image to create a painter for
 * @param offset the subsection of the image to draw
 * @param filterQuality the sampling algorithm applied to the image when it is scaled
 */
@Composable
fun rememberImagePainter(
    image: Image?,
    offset: IntOffset = IntOffset.Zero,
    filterQuality: FilterQuality = FilterQuality.Low,
): Painter? {
    val content = if (image?.content?.isNotEmpty() == true) image.content else null

    // Attempt to convert the image bytes into a bitmap.
    val bitmap = content?.let {
        try {
            // TODO transformations
            content.asImageBitmap()
        } catch (ex: Exception) {
            null
        }
    }

    return bitmap?.let {
        BitmapPainter(
            image = bitmap,
            srcOffset = offset,
            filterQuality = filterQuality
        )
    }
}