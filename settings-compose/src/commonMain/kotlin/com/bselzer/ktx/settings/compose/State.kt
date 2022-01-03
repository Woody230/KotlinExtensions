package com.bselzer.ktx.settings.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.bselzer.ktx.settings.setting.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Remembers the non-null state of the setting.
 */
@Composable
fun <T> Setting<T>.safeState(coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T> {
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
 * Remembers the nullable state of the setting in a safe way using the default value.
 */
@Composable
fun <T> Setting<T>.defaultState(coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T?> =
    nullState(observer = { observe() }, initial = defaultValue, coroutineContext = coroutineContext)

/**
 * Remembers the nullable state of the setting.
 */
@Composable
fun <T> Setting<T>.nullState(coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T?> =
    nullState(observer = { observeOrNull() }, initial = null, coroutineContext = coroutineContext)

/**
 * Remembers the nullable state of the setting.
 */
@Composable
private fun <T> Setting<T>.nullState(observer: () -> Flow<T?>, initial: T?, coroutineContext: CoroutineContext = EmptyCoroutineContext): MutableState<T?> {
    val scope = rememberCoroutineScope { coroutineContext }
    return object : MutableState<T?> {
        private val state = observer().collectAsState(initial, coroutineContext)
        override var value: T?
            get() = state.value
            set(value) {
                scope.launch { if (value == null) remove() else set(value) }
            }

        override fun component1(): T? = value
        override fun component2(): (T?) -> Unit = { value = it }
    }
}


