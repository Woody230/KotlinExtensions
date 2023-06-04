package com.bselzer.ktx.datetime.format

import java.time.format.DateTimeFormatter

actual class FormatStyleDateTimeFormatter actual constructor(
    private val dateStyle: FormatStyle?,
    private val timeStyle: FormatStyle?
) : com.bselzer.ktx.datetime.format.DateTimeFormatter, BaseDateTimeFormatter() {
    override val formatter: DateTimeFormatter
        get() = when {
            dateStyle != null && timeStyle != null -> DateTimeFormatter.ofLocalizedDateTime(dateStyle.asJavaFormatStyle(), timeStyle.asJavaFormatStyle())
            dateStyle != null -> DateTimeFormatter.ofLocalizedDate(dateStyle.asJavaFormatStyle())
            timeStyle != null -> DateTimeFormatter.ofLocalizedTime(timeStyle.asJavaFormatStyle())
            else -> DateTimeFormatter.ofLocalizedDateTime(java.time.format.FormatStyle.FULL)
        }

    private fun FormatStyle.asJavaFormatStyle() = when (this) {
        FormatStyle.FULL -> java.time.format.FormatStyle.FULL
        FormatStyle.LONG -> java.time.format.FormatStyle.LONG
        FormatStyle.MEDIUM -> java.time.format.FormatStyle.MEDIUM
        FormatStyle.SHORT -> java.time.format.FormatStyle.SHORT
    }
}