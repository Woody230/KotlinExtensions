package com.bselzer.ktx.compose.ui.graphics

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
actual fun ByteArray.asImageBitmap(): ImageBitmap = Image.makeFromEncoded(this).toComposeImageBitmap()