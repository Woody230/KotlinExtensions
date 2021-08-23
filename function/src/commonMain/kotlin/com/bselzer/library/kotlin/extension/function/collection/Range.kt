package com.bselzer.library.kotlin.extension.function.collection

/**
 * @return a progression from this value to the specified [to] value, inclusive
 */
infix fun Int.to(to: Int): IntProgression
{
    val step = if (this > to) -1 else 1
    val start = if (this > to) this - 1 else this + 1
    return IntProgression.fromClosedRange(start, to, step)
}

/**
 * @return a progression from this value to the specified [to] value, exclusive
 */
infix fun Int.toExclusive(to: Int): IntProgression
{
    val step = if (this > to) -1 else 1
    val start = if (this > to) this - 1 else this + 1
    val end = if (this > to) to + 1 else to - 1
    return IntProgression.fromClosedRange(start, end, step)
}