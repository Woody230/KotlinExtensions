package com.bselzer.ktx.compose.image.ui.layout

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
internal actual fun ByteArray.asImageBitmap(): ImageBitmap = Image.makeFromEncoded(this).toComposeImageBitmap()