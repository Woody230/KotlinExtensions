package com.bselzer.library.kotlin.extension.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Updates the preference value for [key] to [value] if it is not null. Otherwise, the key is removed.
 * @return the coroutine job
 */
inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.update(key: Preferences.Key<TNullSafe>, value: TNullable, scope: CoroutineScope) =
    scope.launch { update(key, value) }

/**
 * Updates the preference value for [key] to [value] if it is not null. Otherwise, the key is removed.
 * @return the preferences
 */
suspend inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.update(key: Preferences.Key<TNullSafe>, value: TNullable) =
    edit { pref -> value?.let { pref[key] = it as TNullSafe } ?: pref.remove(key) }

/**
 * Removes the preference value for [key].
 * @return the coroutine job
 */
inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.clear(key: Preferences.Key<TNullSafe>, scope: CoroutineScope) = update(key, null, scope)

/**
 * Removes the preference value for [key].
 * @return the preferences
 */
suspend inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.clear(key: Preferences.Key<TNullSafe>) = update(key, null)