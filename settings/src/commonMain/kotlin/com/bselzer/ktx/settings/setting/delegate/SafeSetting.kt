package com.bselzer.ktx.settings.setting.delegate

import com.bselzer.ktx.settings.setting.Setting

/**
 * A wrapper for delegating a non-null value.
 */
class SafeSetting<T>(wrapper: Setting<T>) : DelegateSetting<T>(wrapper)