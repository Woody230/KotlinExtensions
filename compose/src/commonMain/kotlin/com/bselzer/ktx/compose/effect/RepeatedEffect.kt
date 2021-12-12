package com.bselzer.ktx.compose.effect

import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Executes the [block] first, then executes the [delay], and then repeats.
 */
@OptIn(ExperimentalTime::class)
@Composable
fun PreRepeatedEffect(delay: Duration, block: suspend () -> Unit) {
    var temp by remember { mutableStateOf(Int.MIN_VALUE) }
    LaunchedEffect(key1 = temp) {
        block()
        delay(delay)

        // Update the key to start the next block.
        temp += 1
    }
}

/**
 * Executes the [delay] first, then executes the [block], and then repeats.
 */
@OptIn(ExperimentalTime::class)
@Composable
fun PostRepeatedEffect(delay: Duration, block: suspend () -> Unit) {
    var temp by remember { mutableStateOf(Int.MIN_VALUE) }
    LaunchedEffect(key1 = temp) {
        delay(delay)
        block()

        // Update the key to start the next delay.
        temp += 1
    }
}