package com.bselzer.library.kotlin.extension.compose.preference

import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bselzer.library.kotlin.extension.preference.nullLatest
import com.bselzer.library.kotlin.extension.preference.safeLatest
import com.bselzer.library.kotlin.extension.preference.update
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

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

// Using null/safeLatest for default values so that the initial value is persisted without having to wait for the state collection to kick in.

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Int>,
    defaultValue: Int = 0
): MutableState<Int> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Int>,
    defaultValue: Int? = null
): MutableState<Int?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Double>,
    defaultValue: Double = 0.0
): MutableState<Double> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Double>,
    defaultValue: Double? = null
): MutableState<Double?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<String>,
    defaultValue: String = ""
): MutableState<String> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<String>,
    defaultValue: String? = null
): MutableState<String?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Boolean>,
    defaultValue: Boolean = false
): MutableState<Boolean> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Boolean>,
    defaultValue: Boolean? = null
): MutableState<Boolean?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Float>,
    defaultValue: Float = 0.0f
): MutableState<Float> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Float>,
    defaultValue: Float? = null
): MutableState<Float?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Long>,
    defaultValue: Long = 0L
): MutableState<Long> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Long>,
    defaultValue: Long? = null
): MutableState<Long?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.safeRemember(
    key: Preferences.Key<Set<String>>,
    defaultValue: Set<String> = emptySet()
): MutableState<Set<String>> = remember(key, safeLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
fun DataStore<Preferences>.nullRemember(
    key: Preferences.Key<Set<String>>,
    defaultValue: Set<String>? = null
): MutableState<Set<String>?> = remember(key, nullLatest(key, defaultValue))

/**
 * Remembers the preference value for [key] and collects it as a state, defaulting null values to the [defaultValue].
 * @return the preference flow as a mutable state
 */
@Composable
private inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.remember(
    key: Preferences.Key<TNullSafe>,
    defaultValue: TNullable
): MutableState<TNullable> {
    // Set up the state wrapper with the initial non-loaded state.
    // TODO use remember { } when issue is resolved -- couldn't find inline method Landroidx/compose/runtime/EffectsKt;.rememberCoroutineScope$default(Lkotlin/jvm/functions/Function0;ILjava/lang/Object;)Lkotlinx/coroutines/CoroutineScope;
    val state: MutableState<PreferenceEntry<TNullable>> = currentComposer.cache(false) { mutableStateOf(PreferenceEntry.NotLoaded()) }

    // Determine an updated state as the value gets updated.
    data.map { pref -> PreferenceEntry.fromNullable(pref[key]) }
        .onEach { value -> state.value = value }
        .collectAsState(initial = PreferenceEntry.NotLoaded())

    return object : MutableState<TNullable> {
        override var value: TNullable
            get() = when (val mutableEntry = state.value) {
                is PreferenceEntry.NotLoaded -> defaultValue
                is PreferenceEntry.Empty -> defaultValue
                is PreferenceEntry.NotEmpty -> mutableEntry.value
            }
            set(value) {
                state.value = PreferenceEntry.fromNullable(value)
                runBlocking { update(key, value) }
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
        fun <T> fromNullable(value: T?) = if (value == null) Empty() else NotEmpty(value)
    }
}