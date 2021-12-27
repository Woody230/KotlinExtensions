package com.bselzer.ktx.settings.setting.delegate

import com.bselzer.ktx.settings.setting.encapsulation.*

interface DelegableSetting<T> : Exists, Initialize<T>, Setter<T>, Removable, SafeGetter<T>, SafeObserver<T> {
    val key: String
    val defaultValue: T
}