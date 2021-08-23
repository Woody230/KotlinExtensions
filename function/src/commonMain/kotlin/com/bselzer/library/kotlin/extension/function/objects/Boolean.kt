package com.bselzer.library.kotlin.extension.function.objects

/**
 * @return 1 if [this] is true, else 0
 */
fun Boolean.toInt(): Int = if (this) 1 else 0

/**
 * @return false if zero, else true
 */
fun Int.toBoolean(): Boolean = this != 0

/**
 * @return 1 if [this] is true, else 0
 */
fun Boolean.toByte(): Byte = if (this) 1 else 0

/**
 * @return false if zero, else true
 */
fun Byte.toBoolean(): Boolean = this.toInt() != 0