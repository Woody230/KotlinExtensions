package androidx.constraintlayout.utility

import kotlin.math.PI
import kotlin.math.pow

internal object Utils {
    fun radians(degrees: Double) = degrees * PI / 180
    fun degrees(radians: Double) = radians * 180 / PI
    fun pow(first: Double, second: Double) = first.pow(second)
    fun signum(value: Double): Double = when {
        value.isNaN() -> Double.NaN
        value < 0.0 -> -1.0
        value == 0.0 -> 0.0
        else -> 1.0
    }
    fun signum(value: Float): Float = when {
        value.isNaN() -> Float.NaN
        value < 0f -> -1f
        value == 0f -> 0f
        else -> 1f
    }
}