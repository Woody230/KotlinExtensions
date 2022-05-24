package com.bselzer.ktx.compose.image.ui.layout

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
internal actual fun ByteArray.asImageBitmap(): ImageBitmap {
    val bytes = this

    // Note that an exception will be thrown when the size is 0.
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
}