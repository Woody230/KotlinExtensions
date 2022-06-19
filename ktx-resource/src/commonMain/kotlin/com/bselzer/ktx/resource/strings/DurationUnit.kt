package com.bselzer.ktx.resource.strings

import com.bselzer.ktx.resource.KtxResources
import dev.icerock.moko.resources.StringResource
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.*

/**
 * @return the [StringResource] associated with the [DurationUnit]
 */
fun DurationUnit.stringResource(): StringResource = when (this) {
    NANOSECONDS -> KtxResources.strings.nanoseconds
    MICROSECONDS -> KtxResources.strings.microseconds
    MILLISECONDS -> KtxResources.strings.milliseconds
    SECONDS -> KtxResources.strings.seconds
    MINUTES -> KtxResources.strings.minutes
    HOURS -> KtxResources.strings.hours
    DAYS -> KtxResources.strings.days
    else -> throw NotImplementedError("A resource does not exist for DurationUnit $this")
}