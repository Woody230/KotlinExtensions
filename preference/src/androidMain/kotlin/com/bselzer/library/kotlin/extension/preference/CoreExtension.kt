package com.bselzer.library.kotlin.extension.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Updates the preference value for [key] to [value] if it is not null. Otherwise, the key is removed.
 * @return the coroutine job
 */
@Composable
fun <T> DataStore<Preferences>.update(key: Preferences.Key<T>, value: T, scope: CoroutineScope) = scope.launch {
    edit { pref -> if (value != null) pref[key] = value else pref.remove(key) }
}

/**
 * Gets the preference value for [key] and collects it as a state, starting with the given [initialValue] and defaulting null values to the [defaultValue].
 * @return the preference flow as a state
 */
@Composable
fun <T> DataStore<Preferences>.collectAsState(key: Preferences.Key<T>, initialValue: T, defaultValue: T): State<T>
{
    return data.map { pref -> pref[key] ?: defaultValue }.collectAsState(initial = initialValue)
}