package com.bselzer.ktx.datetime.format

import kotlin.time.Instant

interface DateTimeFormatter {
    fun format(instant: Instant): String
}