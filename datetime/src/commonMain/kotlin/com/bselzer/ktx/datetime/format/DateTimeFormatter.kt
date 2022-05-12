package com.bselzer.ktx.datetime.format

import kotlinx.datetime.Instant

interface DateTimeFormatter {
    fun format(instant: Instant): String
}