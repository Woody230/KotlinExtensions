package com.bselzer.ktx.datetime.timer

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

/**
 * Creates a flow that emits the remaining amount of time every [delay].
 * Emitting ceases when the remaining amount of time reaches zero.
 *
 * @param startTime the start time
 * @param duration the amount of time since the [startTime] the countdown should last for
 * @param delay the amount of time to wait before emitting a new value
 * @return the flow of remaining amount of time
 */
@OptIn(ExperimentalTime::class)
fun Clock.countdown(startTime: Instant, duration: Duration, delay: Duration = 1.seconds): Flow<Duration> = flow {
    while (true) {
        val remaining = duration - now().minus(startTime)

        // Stop emitting values once the timer has reached zero.
        if (remaining <= Duration.ZERO) {
            emit(Duration.ZERO)
            break
        }

        emit(remaining)
        delay(delay)
    }
}.conflate() // Only need to care about the latest value so use conflation to ignore slow collection.