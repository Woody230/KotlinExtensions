package com.bselzer.library.kotlin.extension.preference

import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Copyright 2020 Bruno Wieczorek
 * Modifications Copyright 2021 Brandon Selzer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @see <a href="https://github.com/burnoo/compose-remember-preference">compose-remember-preference by Bruno Wieczorek</a>
 */

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Int>,
    initialValue: Int = 0,
    defaultValue: Int = 0,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Int>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Int>,
    initialValue: Int? = null,
    defaultValue: Int? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Int?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Double>,
    initialValue: Double = 0.0,
    defaultValue: Double = 0.0,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Double>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Double>,
    initialValue: Double? = null,
    defaultValue: Double? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Double?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<String>,
    initialValue: String = "",
    defaultValue: String = "",
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<String>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<String>,
    initialValue: String? = null,
    defaultValue: String? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<String?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Boolean>,
    initialValue: Boolean = false,
    defaultValue: Boolean = false,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Boolean>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Boolean>,
    initialValue: Boolean? = null,
    defaultValue: Boolean? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Boolean?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Float>,
    initialValue: Float = 0.0f,
    defaultValue: Float = 0.0f,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Float>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Float>,
    initialValue: Float? = null,
    defaultValue: Float? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Float?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Long>,
    initialValue: Long = 0L,
    defaultValue: Long = 0L,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Long>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Long>,
    initialValue: Long? = null,
    defaultValue: Long? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Long?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Set<String>>,
    initialValue: Set<String> = emptySet(),
    defaultValue: Set<String> = emptySet(),
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Set<String>>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Set<String>>,
    initialValue: Set<String>? = null,
    defaultValue: Set<String>? = null,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<Set<String>?>
{
    return remember(key, initialValue, defaultValue, scope)
}

/**
 * Remembers the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
private inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.remember(
    key: Preferences.Key<TNullSafe>,
    initialValue: TNullable,
    defaultValue: TNullable,
    scope: CoroutineScope = rememberCoroutineScope()
): MutableState<TNullable>
{
    // Set up the state wrapper with the initial non-loaded state.
    val state: MutableState<PreferenceEntry<TNullable>> = remember { mutableStateOf(PreferenceEntry.NotLoaded()) }

    // Determine an updated state as the value gets updated.
    data.map { pref -> PreferenceEntry.fromNullable(pref[key]) }
        .onEach { value -> if (state.value is PreferenceEntry.NotLoaded) state.value = value }
        .collectAsState(initial = PreferenceEntry.NotLoaded())

    return object : MutableState<TNullable>
    {
        override var value: TNullable
            get() = when (val entry = state.value)
            {
                is PreferenceEntry.NotLoaded -> initialValue
                is PreferenceEntry.Empty -> defaultValue
                is PreferenceEntry.NotEmpty -> entry.value
            }
            set(value)
            {
                state.value = PreferenceEntry.fromNullable(value)

                // Need to cast to null-safe version to allow for nullable and null-safe extension methods.
                update(key, value, scope)
            }

        override fun component1(): TNullable = value
        override fun component2(): (TNullable) -> Unit = { value = it }
    }
}

private sealed class PreferenceEntry<out T>
{
    data class NotEmpty<T>(val value: T) : PreferenceEntry<T>()
    class Empty<T> : PreferenceEntry<T>()
    class NotLoaded<T> : PreferenceEntry<T>()

    companion object
    {
        inline fun <reified T> fromNullable(value: T?) = if (value == null) Empty() else NotEmpty(value)
    }
}