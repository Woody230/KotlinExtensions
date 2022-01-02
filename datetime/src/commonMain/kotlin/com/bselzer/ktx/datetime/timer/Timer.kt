package com.bselzer.ktx.datetime.timer

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Formats the duration into a user friendly representation with minutes as the highest unit.
 */
@OptIn(ExperimentalTime::class)
expect fun Duration.minuteFormat(): String