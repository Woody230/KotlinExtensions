package com.bselzer.ktx.datetime.format

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

abstract class BaseDateTimeFormatter : DateTimeFormatter {
    protected abstract val formatter: java.time.format.DateTimeFormatter

    override fun format(instant: Instant): String {
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        return formatter.format(localDate)
    }
}