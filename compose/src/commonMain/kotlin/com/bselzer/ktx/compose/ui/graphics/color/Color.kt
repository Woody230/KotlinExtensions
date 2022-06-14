package com.bselzer.ktx.compose.ui.graphics.color

import androidx.compose.ui.graphics.Color
import kotlin.jvm.JvmInline

/**
 * Value wrapper of a hexadecimal representation of a [Color].
 */
@JvmInline
value class Hex(val value: String)

/**
 * The [Color] associated with an ARGB or RGB hexadecimal representation.
 */
fun Hex.color(): Color {
    var hex = value.removePrefix("#")
    if (hex.length == 6) {
        // Alpha not specified so append full opacity.
        hex = "FF${hex}"
    }
    return Color(hex.toLong(radix = 16))
}

/**
 * The [Color] associated with an ARGB or RGB hexadecimal representation, or null if the [Hex.value] is unable to be converted.
 */
fun Hex.colorOrNull(): Color? = try {
    color()
} catch (ex: Exception) {
    null
}

/**
 * The hexadecimal representation of this [Color] in ARGB format.
 */
fun Color.hex(): Hex {
    fun Float.convert() = (this * 255).toLong().toString(radix = 16).padStart(length = 2, padChar = '0')
    val alpha = alpha.convert()
    val red = red.convert()
    val green = green.convert()
    val blue = blue.convert()
    return Hex("#$alpha$red$green$blue")
}
