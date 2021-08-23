package com.bselzer.library.kotlin.extension.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * The adapter for converting an object into an integer.
 */
class IntegerAdapter
{
    @Retention(AnnotationRetention.RUNTIME)
    @JsonQualifier
    annotation class BoolInt

    /**
     * @return the integer converted into json
     */
    @ToJson
    fun toJson(@BoolInt value: Int): String
    {
        return value.toString()
    }

    /**
     * @return the json converted into an integer
     */
    @FromJson
    @BoolInt
    fun fromJson(json: Any): Int
    {
        return when (json)
        {

            is Double ->
            {
                try
                {
                    json.toInt()
                } catch (e: ClassCastException)
                {
                    throw JsonDataException("Expected an Integer for NUMBER = $json")
                }
            }

            is String ->
            {
                try
                {
                    Integer.valueOf(json)
                } catch (e: ClassCastException)
                {
                    throw JsonDataException("Expected NUMBER but received string = $json")
                }
            }

            is Boolean ->
            {
                if (json) 1 else 0
            }

            else -> throw JsonDataException("Expected NUMBER or BOOLEAN but received $json")
        }
    }
}