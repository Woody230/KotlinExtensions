package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.setting.encapsulation.*

interface Setting<T> : Exists, NullGet<T?>, NullObserve<T?>, Put<T>, Remove, SafeGet<T>, SafeObserve<T>