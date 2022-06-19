package com.bselzer.ktx.datetime.format

import java.time.format.DateTimeFormatter

actual class PatternDateTimeFormatter actual constructor(
    private val pattern: String
) : com.bselzer.ktx.datetime.format.DateTimeFormatter, BaseDateTimeFormatter() {
    override val formatter: DateTimeFormatter
        get() = DateTimeFormatter.ofPattern(pattern)
}