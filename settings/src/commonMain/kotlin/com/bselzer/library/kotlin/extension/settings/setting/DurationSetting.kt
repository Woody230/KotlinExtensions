package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.model.getDuration
import com.bselzer.library.kotlin.extension.settings.model.getDurationOrNull
import com.bselzer.library.kotlin.extension.settings.model.putDuration
import com.russhwolf.settings.Settings
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * The [Settings] wrapper for a [Duration].
 */
@OptIn(ExperimentalTime::class)
class DurationSetting(settings: Settings, key: String, defaultValue: Duration = Duration.ZERO) : SettingWrapper<Duration>(settings, key, defaultValue) {
    override fun get(): Duration = settings.getDuration(key, defaultValue)
    override fun getOrNull(): Duration? = settings.getDurationOrNull(key)
    override fun put(value: Duration) = settings.putDuration(key, value)
}