package com.bselzer.ktx.compose.ui.graphics.painter

import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntOffset
import com.bselzer.ktx.compose.ui.graphics.asImageBitmap

fun ByteArray?.toPainterOrNull(
    /**
     * Optional offset relative to image used to draw a subsection of the ImageBitmap. By default this uses the origin of image
     */
    srcOffset: IntOffset = IntOffset.Zero,

    /**
     * Sampling algorithm applied to the image when it is scaled and drawn into the destination.
     * The default is FilterQuality.Low which scales using a bilinear sampling algorithm
     */
    filterQuality: FilterQuality = FilterQuality.Low,
): Painter? {
    // Ignore empty bytes which will cause exceptions when trying to create a bitmap.
    val content = if (this?.isNotEmpty() == true) this else null
    val bitmap = content?.let {
        try {
            content.asImageBitmap()
        } catch (ex: Exception) {
            null
        }
    }

    return bitmap?.let {
        BitmapPainter(
            image = bitmap,
            srcOffset = srcOffset,
            filterQuality = filterQuality
        )
    }
}