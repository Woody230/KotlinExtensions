package com.bselzer.ktx.serialization.context

import com.bselzer.ktx.serialization.merge.ArrayMergeHandling
import com.bselzer.ktx.serialization.merge.JsonMergeOptions
import com.bselzer.ktx.serialization.merge.NullMergeHandling
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.math.max

open class JsonContext(
    val instance: Json
) : StringFormatContext(instance) {
    companion object Default : JsonContext(Json)

    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromJsonElement(deserializer, JsonPrimitive(this))

    fun JsonElement.merge(
        other: JsonElement?,
        options: JsonMergeOptions = JsonMergeOptions()
    ): JsonElement = when {
        other is JsonNull || other == null -> mergeNull(options)
        this is JsonObject && other is JsonObject -> merge(other, options)
        this is JsonArray && other is JsonArray -> merge(other, options)
        else -> other
    }

    private fun JsonElement.mergeNull(
        options: JsonMergeOptions = JsonMergeOptions.Default
    ) = when (options.nullHandling) {
        NullMergeHandling.IGNORE -> this
        NullMergeHandling.MERGE -> JsonNull
    }

    fun JsonObject.merge(
        other: JsonObject,
        options: JsonMergeOptions = JsonMergeOptions.Default
    ): JsonObject {
        val keys = keys + other.keys
        val elements = mutableMapOf<String, JsonElement>()
        for (key in keys) {
            val thisValue = this[key] ?: JsonNull
            val otherValue = other[key]
            elements[key] = thisValue.merge(otherValue, options)
        }

        return JsonObject(elements)
    }

    fun JsonArray.merge(
        other: JsonArray,
        options: JsonMergeOptions = JsonMergeOptions.Default
    ): JsonArray = when (options.arrayHandling) {
        ArrayMergeHandling.CONCAT -> concat(other)
        ArrayMergeHandling.UNION -> union(other)
        ArrayMergeHandling.REPLACE -> other
        ArrayMergeHandling.MERGE -> replaceByIndex(other, options)
    }

    private fun JsonArray.concat(other: JsonArray): JsonArray {
        val elements = plus(other)
        return JsonArray(elements)
    }

    private fun JsonArray.union(other: JsonArray): JsonArray {
        val elements = plus(other).distinct()
        return JsonArray(elements)
    }

    private fun JsonArray.replaceByIndex(
        other: JsonArray,
        options: JsonMergeOptions
    ): JsonArray {
        val elements = mutableListOf<JsonElement>()

        for (index in 0 until max(size, other.size)) {
            val thisElement = getOrNull(index) ?: JsonNull
            val otherElement = other.getOrNull(index)
            val mergedElement = thisElement.merge(otherElement, options)
            elements.add(mergedElement)
        }

        return JsonArray(elements)
    }
}