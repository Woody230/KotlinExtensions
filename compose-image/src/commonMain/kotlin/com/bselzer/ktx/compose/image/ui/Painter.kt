package com.bselzer.ktx.compose.image.ui

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntOffset
import com.bselzer.ktx.compose.image.cache.instance.ImageCache
import com.bselzer.ktx.compose.image.model.Image
import com.bselzer.ktx.kodein.db.transaction.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
internal expect fun ByteArray.asImageBitmap(): ImageBitmap

/**
 * The cache for retrieving images.
 */
val LocalImageCache: ProvidableCompositionLocal<ImageCache> = staticCompositionLocalOf { ImageCache() }

/**
 * Creates a painter with the byte content from the image at the given [url].
 *
 * @param cache the image cache
 * @param url the url to retrieve the image from
 * @param offset the subsection of the image to draw
 * @param filterQuality the sampling algorithm applied to the image when it is scaled
 */
@Composable
fun Transaction.rememberImagePainter(
    cache: ImageCache = LocalImageCache.current,
    url: String?,
    offset: IntOffset = IntOffset.Zero,
    filterQuality: FilterQuality = FilterQuality.Low,
): Painter? {
    val image: Image? by produceState<Image?>(initialValue = null, key1 = url) {
        value = url?.let {
            withContext(Dispatchers.Default) {
                val image = with(cache) { getImage(url) }

                // Ignore defaulted images (no bytes).
                return@withContext if (image.content.isEmpty()) null else image
            }
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