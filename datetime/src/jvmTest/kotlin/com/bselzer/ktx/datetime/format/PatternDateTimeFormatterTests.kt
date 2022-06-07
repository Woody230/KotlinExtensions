package com.bselzer.ktx.datetime.format

import com.bselzer.ktx.intl.DefaultLocale
import com.bselzer.ktx.intl.Localizer
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
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

    @Test
    fun dayOfWeek() {
        val instant = Instant.parse("2022-05-12T11:00:00.000Z")
        val date = instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        val javaFormatter = DateTimeFormatter.ofPattern("EEEE")
        assertEquals("Thursday", javaFormatter.format(date))
        assertEquals("jeudi", javaFormatter.withLocale(Locale.FRENCH).format(date))
        assertEquals("jueves", javaFormatter.withLocale(Locale.Builder().setLanguage("es").setRegion("ES").build()).format(date))

        val kotlinFormatter = PatternDateTimeFormatter("EEEE")
        assertEquals("Thursday", kotlinFormatter.format(instant))

        DefaultLocale = Localizer.FRENCH
        assertEquals("jeudi", kotlinFormatter.format(instant))

        DefaultLocale = Localizer.SPANISH
        assertEquals("jueves", kotlinFormatter.format(instant))
    }
}