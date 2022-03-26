package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.settings.model.getDuration
import com.bselzer.ktx.settings.model.getDurationOrNull
import com.bselzer.ktx.settings.model.putDuration
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlin.time.Duration

/**
 * The [Settings] wrapper for a [Duration].
 */
@OptIn(ExperimentalSettingsApi::class)
class DurationSetting(settings: SuspendSettings, key: String, defaultValue: Duration = Duration.ZERO) : SettingWrapper<Duration>(settings, key, defaultValue) {
    override suspend fun get(): Duration = settings.getDuration(key, defaultValue)
    override suspend fun getOrNull(): Duration? = settings.getDurationOrNull(key)
    override suspend fun put(value: Duration) = settings.putDuration(key, value)
}