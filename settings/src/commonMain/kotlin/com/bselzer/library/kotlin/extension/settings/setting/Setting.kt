package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.setting.encapsulation.*

interface Setting<T> : Exists, Initialize<T>, NullGetter<T?>, NullObserver<T?>, Setter<T>, Removable, SafeGetter<T>, SafeObserver<T> {
    val key: String
    val defaultValue: T
}