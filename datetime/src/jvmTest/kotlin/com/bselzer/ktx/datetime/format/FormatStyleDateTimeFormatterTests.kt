package com.bselzer.ktx.datetime.format

import kotlinx.datetime.Instant
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class FormatStyleDateTimeFormatterTests {
    @Test
    fun dateTime() {
        val instant = Instant.parse("2022-05-12T11:10:54.298Z")
        val formatter = FormatStyleDateTimeFormatter(FormatStyle.SHORT, FormatStyle.SHORT)

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT+12"))
        assertEquals("5/11/22, 11:10 PM", formatter.format(instant))

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dacca"))
        assertEquals("5/12/22, 5:10 PM", formatter.format(instant))

        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Kiritimati"))
        assertEquals("5/13/22, 1:10 AM", formatter.format(instant))
    }
}