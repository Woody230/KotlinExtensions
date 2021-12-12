package com.bselzer.ktx.settings.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.bselzer.ktx.settings.setting.delegate.DelegateSetting
import com.bselzer.ktx.settings.setting.delegate.NullSetting
import com.bselzer.ktx.settings.setting.delegate.SafeSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Remembers the non-null state of the setting.
 */
@Composable
fun <T> DelegateSetting<T>.safeState(coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T> {
    val scope = rememberCoroutineScope { coroutineContext }
    return object : MutableState<T> {
        private val state = observe().collectAsState(defaultValue, coroutineContext)
        override var value: T
            get() = state.value
            set(value) {
                scope.launch { set(value) }
            }

        override fun component1(): T = value
        override fun component2(): (T) -> Unit = { value = it }
    }
}

/**
 * Remembers the non-null state of the setting as a nullable state.
 */
@Composable
fun <T> SafeSetting<T>.nullState(coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T?> =
    nullState(observer = { observe() }, coroutineContext = coroutineContext)

/**
 * Remembers the nullable state of the setting.
 */
@Composable
fun <T> NullSetting<T>.nullState(coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T?> =
    nullState(observer = { observeOrNull() }, coroutineContext = coroutineContext)

/**
 * Remembers the nullable state of the setting.
 */
@Composable
private fun <T> DelegateSetting<T>.nullState(observer: () -> Flow<T?>, coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T?> {
    val scope = rememberCoroutineScope { coroutineContext }
    return object : MutableState<T?> {
        private val state = observer().collectAsState(defaultValue, coroutineContext)
        override var value: T?
            get() = state.value
            set(value) {
                scope.launch { if (value == null) remove() else set(value) }
            }

        override fun component1(): T? = value
        override fun component2(): (T?) -> Unit = { value = it }
    }
}

