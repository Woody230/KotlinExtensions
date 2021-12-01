package com.bselzer.library.kotlin.extension.settings.compose.delegate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.bselzer.library.kotlin.extension.settings.setting.delegate.SafeSetting

/**
 * @return this [SafeSetting] delegating a non-null [MutableState].
 */
@Composable
fun <T> SafeSetting<T>.safeState(): MutableState<T> = object : MutableState<T> {
    override var value: T by this@safeState
    override fun component1(): T = value
    override fun component2(): (T) -> Unit = { value = it }
}

/**
 * @return this [SafeSetting] delegating a nullable [MutableState].
 */
@Composable
fun <T> SafeSetting<T>.nullableState(): MutableState<T?> = object : MutableState<T?> {
    override var value: T? by this@nullableState
    override fun component1(): T? = value
    override fun component2(): (T?) -> Unit = { value = it }
}