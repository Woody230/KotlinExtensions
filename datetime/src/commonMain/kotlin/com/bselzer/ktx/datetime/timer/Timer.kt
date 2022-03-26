package com.bselzer.ktx.datetime.timer

import kotlin.time.Duration

/**
 * Formats the duration into a user friendly representation with minutes as the highest unit.
 */
expect fun Duration.minuteFormat(): String