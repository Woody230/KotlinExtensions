package com.bselzer.library.kotlin.extension.moshi.android

import android.util.Log
import com.squareup.moshi.*
import java.lang.reflect.Type

/**
 * The adapter for handling errors in lists.
 * https://stackoverflow.com/questions/54145519/moshi-adapter-to-skip-bad-objects-in-the-listt
 */
class SkipErrorListAdapter<Value>(moshi: Moshi, valueType: Type) : JsonAdapter<List<Value?>>()
{
    /**
     * The adapter for converting the value.
     */
    private val valueAdapter: JsonAdapter<Value> = moshi.adapter(valueType)

    @FromJson
    override fun fromJson(reader: JsonReader): List<Value?>
    {
        val list = mutableListOf<Value?>()

        reader.beginArray()
        while (reader.hasNext())
        {
            try
            {
                val peeked = reader.peekJson()
                list += valueAdapter.fromJson(peeked)
            }
            catch (ex: JsonDataException)
            {
                Log.e("adapter", ex.stackTraceToString())
            }

            reader.skipValue()
        }

        reader.endArray()
        return list
    }

    @ToJson
    override fun toJson(writer: JsonWriter, list: List<Value?>?)
    {
        list ?: throw NullPointerException("list was null! Wrap in .nullSafe() to list nullable values.")

        writer.beginArray()

        list.indices.forEach { i ->
            valueAdapter.toJson(writer, list[i])
        }

        writer.endArray()
    }
}