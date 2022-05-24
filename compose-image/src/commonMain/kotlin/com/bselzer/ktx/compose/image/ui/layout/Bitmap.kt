package com.bselzer.ktx.compose.image.ui.layout

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
internal expect fun ByteArray.asImageBitmap(): ImageBitmap