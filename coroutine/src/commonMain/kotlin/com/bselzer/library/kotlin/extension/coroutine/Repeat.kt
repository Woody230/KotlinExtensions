package com.bselzer.library.kotlin.extension.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Executes the [block] first, then executes the [delay], and then repeats.
 */
@OptIn(ExperimentalTime::class)
suspend fun CoroutineScope.preRepeat(interval: Duration, block: suspend CoroutineScope.() -> Unit) {
    while (true) {
        block(this)
        delay(interval)
    }
}

/**
 * Executes the [delay] first, then executes the [block], and then repeats.
 */
@OptIn(ExperimentalTime::class)
suspend fun CoroutineScope.postRepeat(interval: Duration, block: suspend CoroutineScope.() -> Unit) {
    while (true) {
        delay(interval)
        block(this)
    }
}