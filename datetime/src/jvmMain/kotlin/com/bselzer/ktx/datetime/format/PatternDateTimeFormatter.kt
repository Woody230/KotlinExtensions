package com.bselzer.ktx.datetime.format

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

actual class PatternDateTimeFormatter actual constructor(pattern: String) : DateTimeFormatter {
    private val formatter = java.time.format.DateTimeFormatter.ofPattern(pattern)

    override fun format(instant: Instant): String {
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        return formatter.format(localDate)
    }
}