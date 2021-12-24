package com.bselzer.ktx.compose.image.ui

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntOffset
import com.bselzer.ktx.compose.image.db.instance.ImageCache
import com.bselzer.ktx.compose.image.model.Image
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
internal expect fun ByteArray.asImageBitmap(): ImageBitmap

/**
 * The dispatcher for retrieving images.
 */
val LocalImageDispatcher = staticCompositionLocalOf { Dispatchers.Default }

/**
 * Creates a painter with the byte content from the image at the given [url].
 *
 * @param cache the image cache
 * @param url the url to retrieve the image from
 * @param offset the subsection of the image to draw
 * @param filterQuality the sampling algorithm applied to the image when it is scaled
 */
@Composable
fun rememberImagePainter(
    cache: ImageCache,
    url: String?,
    offset: IntOffset = IntOffset.Zero,
    filterQuality: FilterQuality = FilterQuality.Low,
    context: CoroutineContext = LocalImageDispatcher.current,
): Painter? {
    // Attempt to load the image from the cache.
    val image: Image? by produceState<Image?>(null) {
        value = url?.let {
            withContext(context) {
                val image = cache.getImage(url = url)

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

    return remember(bitmap) {
        bitmap?.let {
            BitmapPainter(
                image = bitmap,
                srcOffset = offset,
                filterQuality = filterQuality
            )
        }
    }
}