package com.bselzer.ktx.settings.compose.setting

import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.settings.compose.model.getColor
import com.bselzer.ktx.settings.compose.model.getColorOrNull
import com.bselzer.ktx.settings.compose.model.putColor
import com.bselzer.ktx.settings.setting.SettingWrapper
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Color].
 */
@OptIn(ExperimentalSettingsApi::class)
class ColorSetting(settings: SuspendSettings, key: String, defaultValue: Color = Color.Black) : SettingWrapper<Color>(settings, key, defaultValue) {
    override suspend fun get(): Color = settings.getColor(key, defaultValue)
    override suspend fun getOrNull(): Color? = settings.getColorOrNull(key)
    override suspend fun put(value: Color) = settings.putColor(key, value)
}