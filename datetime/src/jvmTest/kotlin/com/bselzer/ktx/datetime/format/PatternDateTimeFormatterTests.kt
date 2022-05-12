package com.bselzer.ktx.datetime.format

import kotlinx.datetime.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PatternDateTimeFormatterTests {
    @Test
    fun instant() {
        val instant = Instant.parse("2022-05-12T11:00:00.000Z")
        val formatter = PatternDateTimeFormatter("MM/dd/yyyy")

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT+12"))
        assertEquals("05/11/2022", formatter.format(instant))

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dacca"))
        assertEquals("05/12/2022", formatter.format(instant))

        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Kiritimati"))
        assertEquals("05/13/2022", formatter.format(instant))
    }
}