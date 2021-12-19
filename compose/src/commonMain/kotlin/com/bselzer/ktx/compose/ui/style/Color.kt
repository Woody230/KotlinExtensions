package com.bselzer.ktx.compose.ui.style

import androidx.compose.ui.graphics.Color
import kotlin.jvm.JvmInline

/**
 * Value wrapper of a hexadecimal representation of a [Color].
 */
@JvmInline
value class Hex(val value: String)

/**
 * The [Color] associated with a hexadecimal representation.
 */
fun Hex.color(): Color = Color(value.removePrefix("#").toInt(radix = 16))
