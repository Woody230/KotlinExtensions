package com.bselzer.ktx.compose.image.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
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
 * Creates a painter with the byte content from the image associated with the [url].
 *
 * @param url the url to retrieve the image from
 * @param getImage the block for retrieving the image from the [url]
 * @param offset the subsection of the image to draw
 * @param filterQuality the sampling algorithm applied to the image when it is scaled
 */
@Composable
fun rememberImagePainter(
    url: String,
    getImage: suspend (String) -> Image?,
    offset: IntOffset = IntOffset.Zero,
    filterQuality: FilterQuality = FilterQuality.Low,
): Painter? {
    val image by produceState<Image?>(initialValue = null, key1 = url) {
        value = run {
            val image = getImage(url)
            if (image?.content?.isNotEmpty() == true) image else null
        }
    }

    // Attempt to convert the image bytes into a bitmap.
    val bitmap = image?.let {
        try {
            // TODO transformations
            it.content.asImageBitmap()
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