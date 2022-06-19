package com.bselzer.ktx.datetime.format

expect class FormatStyleDateTimeFormatter(dateStyle: FormatStyle?, timeStyle: FormatStyle?) : DateTimeFormatter

/**
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/FormatStyle.html">FormatStyle</a>
 */
enum class FormatStyle {
    /**
     * Full text style, with the most detail. For example, the format might be 'Tuesday, April 12, 1952 AD' or '3:30:42pm PST'.
     */
    FULL,

    /**
     * Long text style, with lots of detail. For example, the format might be 'January 12, 1952'.
     */
    LONG,

    /**
     * Medium text style, with some detail. For example, the format might be 'Jan 12, 1952'.
     */
    MEDIUM,

    /**
     * Short text style, typically numeric. For example, the format might be '12.13.52' or '3:30pm'.
     */
    SHORT
}