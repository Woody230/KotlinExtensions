package com.bselzer.ktx.compose.image.ui.layout

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
expect fun ByteArray.asImageBitmap(): ImageBitmap