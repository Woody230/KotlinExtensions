package com.bselzer.ktx.coroutine.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration

/**
 * Executes the [block] first, then executes the [delay], and then repeats.
 */
suspend fun CoroutineScope.preRepeat(interval: Duration, block: suspend CoroutineScope.() -> Unit) {
    while (true) {
        block(this)
        delay(interval)
    }
}

/**
 * Executes the [delay] first, then executes the [block], and then repeats.
 */
suspend fun CoroutineScope.postRepeat(interval: Duration, block: suspend CoroutineScope.() -> Unit) {
    while (true) {
        delay(interval)
        block(this)
    }
}