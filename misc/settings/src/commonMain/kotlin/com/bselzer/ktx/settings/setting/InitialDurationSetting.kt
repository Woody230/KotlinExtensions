package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * The [Settings] wrapper for maintaining the initial amount and unit that normally would be lost when using the [DurationSetting].
 */
@OptIn(ExperimentalSettingsApi::class)
class InitialDurationSetting(settings: SuspendSettings, override val key: String, val initialAmount: Int, val initialUnit: DurationUnit) :
    Setting<Duration> by DurationSetting(settings = settings, key = key, defaultValue = initialAmount.toDuration(initialUnit))