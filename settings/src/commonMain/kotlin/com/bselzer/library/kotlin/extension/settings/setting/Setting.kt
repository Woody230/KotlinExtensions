package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.setting.encapsulation.*

interface Setting<T> : Exists, Initialize<T>, NullGetter<T?>, Setter<T>, Removable, SafeGetter<T> {
    val key: String
    val defaultValue: T
}