package com.bselzer.ktx.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * A wrapper for storing preferences using the DataStore library with the AndroidX Preference library.
 */
class DataStoreWrapper(private val datastore: DataStore<Preferences>, private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : PreferenceDataStore()
{
    override fun getBoolean(key: String, defValue: Boolean): Boolean = datastore.safeLatest(booleanPreferencesKey(key), defValue)
    override fun putBoolean(key: String, value: Boolean)
    {
        datastore.update(booleanPreferencesKey(key), value, scope)
    }

    override fun getFloat(key: String, defValue: Float): Float = datastore.safeLatest(floatPreferencesKey(key), defValue)
    override fun putFloat(key: String, value: Float)
    {
        datastore.update(floatPreferencesKey(key), value, scope)
    }

    override fun getInt(key: String, defValue: Int): Int = datastore.safeLatest(intPreferencesKey(key), defValue)
    override fun putInt(key: String, value: Int)
    {
        datastore.update(intPreferencesKey(key), value, scope)
    }

    override fun getLong(key: String, defValue: Long): Long = datastore.safeLatest(longPreferencesKey(key), defValue)
    override fun putLong(key: String, value: Long)
    {
        datastore.update(longPreferencesKey(key), value, scope)
    }

    override fun getString(key: String, defValue: String?): String? = datastore.nullLatest(stringPreferencesKey(key), defValue)
    override fun putString(key: String, value: String?)
    {
        datastore.update(stringPreferencesKey(key), value, scope)
    }

    override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? = datastore.nullLatest(stringSetPreferencesKey(key), defValues)?.toMutableSet()
    override fun putStringSet(key: String, values: MutableSet<String>?)
    {
        datastore.update(stringSetPreferencesKey(key), values, scope)
    }
}