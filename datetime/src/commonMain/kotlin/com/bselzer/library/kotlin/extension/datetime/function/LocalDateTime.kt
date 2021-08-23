package com.bselzer.library.kotlin.extension.datetime.function

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * The minimum date time value.
 */
fun LocalDateTime.Companion.minValue(): LocalDateTime = Instant.DISTANT_PAST.toLocalDateTime(TimeZone.UTC)

/**
 * The maximum date time value.
 */
fun LocalDateTime.Companion.maxValue(): LocalDateTime = Instant.DISTANT_FUTURE.toLocalDateTime(TimeZone.UTC)