package com.bselzer.library.kotlin.extension.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsInt")
fun DataStore<Preferences>.exists(key: Preferences.Key<Int>) = nullLatest(key, defaultValue = null) != null

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsDouble")
fun DataStore<Preferences>.exists(key: Preferences.Key<Double>) = nullLatest(key, defaultValue = null) != null

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsString")
fun DataStore<Preferences>.exists(key: Preferences.Key<String>) = nullLatest(key, defaultValue = null) != null

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsBoolean")
fun DataStore<Preferences>.exists(key: Preferences.Key<Boolean>) = nullLatest(key, defaultValue = null) != null

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsFloat")
fun DataStore<Preferences>.exists(key: Preferences.Key<Float>) = nullLatest(key, defaultValue = null) != null

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsLong")
fun DataStore<Preferences>.exists(key: Preferences.Key<Long>) = nullLatest(key, defaultValue = null) != null

/**
 * Determines whether the preference exists.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return true if the preference key exists and its associated value is not null
 */
@JvmName("existsStringSet")
fun DataStore<Preferences>.exists(key: Preferences.Key<Set<String>>) = nullLatest(key, defaultValue = null) != null