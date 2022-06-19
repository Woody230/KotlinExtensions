package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.settings.setting.encapsulation.*

interface Setting<T> : Exists, Initialize<T>, NullGetter<T?>, Setter<T>, Removable, SafeGetter<T>, SafeObserver<T>, NullObserver<T> {
    val key: String
    val defaultValue: T
}