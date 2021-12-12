package com.bselzer.ktx.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<Int>, defaultValue: Int = 0)
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<Double>, defaultValue: Double = 0.0)
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<String>, defaultValue: String = "")
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<Boolean>, defaultValue: Boolean = false)
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<Float>, defaultValue: Float = 0f)
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<Long>, defaultValue: Long = 0)
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}

/**
 * Sets the preference value for [key] to [defaultValue] if the preference does not exist.
 *
 * This method ideally should not be used on the main thread because it uses runBlocking.
 */
suspend fun DataStore<Preferences>.initialize(key: Preferences.Key<Set<String>>, defaultValue: Set<String> = emptySet())
{
    if (nullLatest(key, defaultValue = null) == null)
    {
        update(key, defaultValue)
    }
}