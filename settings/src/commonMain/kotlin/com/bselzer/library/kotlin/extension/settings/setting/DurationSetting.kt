package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.model.getDuration
import com.bselzer.library.kotlin.extension.settings.model.getDurationOrNull
import com.bselzer.library.kotlin.extension.settings.model.putDuration
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * The [Settings] wrapper for a [Duration].
 */
@OptIn(ExperimentalSettingsApi::class, ExperimentalTime::class)
class DurationSetting(settings: SuspendSettings, key: String, defaultValue: Duration = Duration.ZERO) : SettingWrapper<Duration>(settings, key, defaultValue) {
    override suspend fun get(): Duration = settings.getDuration(key, defaultValue)
    override suspend fun getOrNull(): Duration? = settings.getDurationOrNull(key)
    override suspend fun put(value: Duration) = settings.putDuration(key, value)
}