package com.bselzer.ktx.datetime.format

import kotlin.time.Duration

/**
 * Formats the duration into a user friendly representation with minutes as the highest unit.
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html">formatting</a>
 */
actual fun Duration.minuteFormat(): String {
    val totalSeconds = inWholeSeconds
    val seconds: Int = (totalSeconds % 60).toInt()
    val minutes: Int = (totalSeconds / 60).toInt()
    return "%01d:%02d".format(minutes, seconds)
}