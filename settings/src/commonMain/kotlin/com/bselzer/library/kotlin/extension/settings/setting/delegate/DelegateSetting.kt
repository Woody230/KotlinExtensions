package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting
import kotlin.properties.ReadWriteProperty

/**
 * The base class for delegating getting/setting a [Setting]
 */
abstract class DelegateSetting<T>(protected val wrapper: Setting<T>) : ReadWriteProperty<Any?, T?>