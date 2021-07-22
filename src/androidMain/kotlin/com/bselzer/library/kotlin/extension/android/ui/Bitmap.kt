package com.bselzer.library.kotlin.extension.android.ui

import android.graphics.*

/**
 * @return a copy of the bitmap drawn in [color]
 */
fun Bitmap.changeColor(color: Int): Bitmap
{
    // Hardware bitmaps are always immutable so specify a particular config to use instead of the original bitmap's config.
    val copy = copy(Bitmap.Config.ARGB_8888, true)
    val paint = Paint().apply {
        colorFilter = LightingColorFilter(color, 1)
    }

    // Apply the color to the new bitmap.
    Canvas(copy).apply {
        drawBitmap(copy, 0f, 0f, paint)
    }

    return copy
}

/**
 * @return a new bitmap with this bitmap in the background and then the [foreground] is drawn on top
 */
fun Bitmap.overlay(foreground: Bitmap): Bitmap
{
    val overlay = Bitmap.createBitmap(this.width, this.height, this.config)

    Canvas(overlay).apply {
        // Draw the background.
        drawBitmap(this@overlay, 0f, 0f, null)

        // Draw the foreground on top.
        drawBitmap(foreground, 0f, 0f, null)
    }

    return overlay
}