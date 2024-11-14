package com.bselzer.ktx.compose.ui.graphics

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Converts a [ByteArray] into an [ImageBitmap].
 */
expect fun ByteArray.asImageBitmap(): ImageBitmap