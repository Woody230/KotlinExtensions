package com.bselzer.ktx.datetime.format

import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

data class DurationBound(
    val min: Duration = 0.days,
    val max: Duration = Int.MAX_VALUE.days
) {
    /**
     * Creates the range between the [min] and [max] inclusive, where the minimum bound must be at least 1.
     */
    fun minRange(unit: DurationUnit): IntRange {
        // Converted minimum can produce 0 because of being rounded down so set the minimum bound to at least 1. (ex: 30 seconds => 0 minutes)
        val convertedMin = max(1, min.toInt(unit))
        val convertedMax = max.toInt(unit)
        return convertedMin..convertedMax
    }

    /**
     * Binds the [amount] between the [min] and [max], where the minimum bound must be at least 1.
     */
    fun minBind(amount: Int, unit: DurationUnit): Int {
        val range = minRange(unit)
        return min(range.last, max(range.first, amount))
    }
}