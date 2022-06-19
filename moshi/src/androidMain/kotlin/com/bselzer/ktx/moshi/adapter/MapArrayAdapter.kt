package com.bselzer.ktx.moshi.adapter

import com.squareup.moshi.*
import java.lang.reflect.Type

/**
 * The adapter for converting json arrays to a map.
 * https://github.com/square/moshi/blob/master/moshi/src/main/java/com/squareup/moshi/MapJsonAdapter.java
 */
class MapArrayAdapter<Key, Value>(moshi: Moshi, keyType: Type, valueType: Type) : JsonAdapter<Map<Key?, Value?>>()
{
    /**
     * The adapter for converting the key.
     */
    private val keyAdapter: JsonAdapter<Key> = moshi.adapter(keyType)

    /**
     * The adapter for converting the value.
     */
    private val valueAdapter: JsonAdapter<Value> = moshi.adapter(valueType)

    @Retention(AnnotationRetention.RUNTIME)
    @JsonQualifier
    annotation class MapArray

    /**
     * @return the map converted to json
     */
    @ToJson
    override fun toJson(writer: JsonWriter, @MapArray map: Map<Key?, Value?>?)
    {
        map ?: throw NullPointerException("map was null! Wrap in .nullSafe() to list nullable values.")

        writer.beginArray()

        map.forEach { entry ->
            writer.beginArray()

            entry.key ?: throw JsonDataException("Map key is null at " + writer.path)
            keyAdapter.toJson(writer, entry.key)
            valueAdapter.toJson(writer, entry.value)

            writer.endArray()
        }

        writer.endArray()
    }

    /**
     * @return the json in the form of an array of arrays into a map
     */
    @FromJson
    @MapArray
    override fun fromJson(reader: JsonReader): Map<Key?, Value?>
    {
        val result = linkedMapOf<Key?, Value?>()
        reader.beginArray()

        while (reader.hasNext())
        {
            reader.beginArray()

            val key: Key? = keyAdapter.fromJson(reader)
            val value: Value? = valueAdapter.fromJson(reader)
            val replaced: Value? = result.put(key, value)

            // Don't allow duplicates.
            if (replaced != null)
            {
                throw JsonDataException("Map key $key has multiple values at path ${reader.path}: $replaced and $value")
            }

            reader.endArray()
        }

        reader.endArray()
        return result
    }
}