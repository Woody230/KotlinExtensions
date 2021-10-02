package com.bselzer.library.kotlin.extension.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking

/**
 * Gets the latest preference value for [key].
 *
 * The use of this method should be limited because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
@OptIn(ExperimentalCoroutinesApi::class)
private inline fun <reified TNullable, reified TNullSafe : TNullable> DataStore<Preferences>.latest(key: Preferences.Key<TNullSafe>, defaultValue: TNullable): TNullable =
    runBlocking {
        data.mapLatest { pref -> pref[key] ?: defaultValue }.firstOrNull() as TNullable ?: defaultValue
    }

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<Int>, defaultValue: Int = 0): Int = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<Int>, defaultValue: Int? = null): Int? = latest(key, defaultValue)


/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<Double>, defaultValue: Double = 0.0): Double = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key].  If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<Double>, defaultValue: Double? = null): Double? = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<String>, defaultValue: String = ""): String = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<String>, defaultValue: String? = null): String? = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<Boolean>, defaultValue: Boolean = false): Boolean = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<Boolean>, defaultValue: Boolean? = null): Boolean? = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<Float>, defaultValue: Float = 0f): Float = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<Float>, defaultValue: Float? = null): Float? = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<Long>, defaultValue: Long = 0): Long = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<Long>, defaultValue: Long? = null): Long? = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.safeLatest(key: Preferences.Key<Set<String>>, defaultValue: Set<String> = emptySet()): Set<String> = latest(key, defaultValue)

/**
 * Gets the latest preference value for [key]. If the key does not exist or the value is null then the [defaultValue] is used.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 * @return the latest value associated with the [key]
 */
fun DataStore<Preferences>.nullLatest(key: Preferences.Key<Set<String>>, defaultValue: Set<String>? = null): Set<String>? = latest(key, defaultValue)