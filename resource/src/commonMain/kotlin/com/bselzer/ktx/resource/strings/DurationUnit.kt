package com.bselzer.ktx.resource.strings

import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.StringResource
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.*

/**
 * @return the [StringResource] associated with the [DurationUnit]
 */
fun DurationUnit.stringResource(): StringResource = when (this) {
    NANOSECONDS -> Resources.strings.nanoseconds
    MICROSECONDS -> Resources.strings.microseconds
    MILLISECONDS -> Resources.strings.milliseconds
    SECONDS -> Resources.strings.seconds
    MINUTES -> Resources.strings.minutes
    HOURS -> Resources.strings.hours
    DAYS -> Resources.strings.days
    else -> throw NotImplementedError("A resource does not exist for DurationUnit $this")
}