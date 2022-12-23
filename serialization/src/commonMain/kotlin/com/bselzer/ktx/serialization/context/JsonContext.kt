package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*
import kotlin.math.max

sealed class JsonContext(
    override val instance: Json
) : StringFormatContext(instance) {
    companion object Default : JsonContext(Json)

    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromJsonElement(deserializer, JsonPrimitive(this))

    fun JsonElement.merge(other: JsonElement?, options: JsonMergeOptions = JsonMergeOptions()): JsonElement = when {
        other is JsonNull || other == null -> this
        this is JsonObject && other is JsonObject -> merge(other)
        this is JsonArray && other is JsonArray -> merge(other, options)
        else -> other
    }

    private fun JsonObject.merge(other: JsonObject): JsonObject {
        val keys = keys + other.keys
        val elements = mutableMapOf<String, JsonElement>()
        for (key in keys) {
            val thisValue = this[key] ?: JsonNull
            val otherValue = other[key]
            elements[key] = thisValue.merge(otherValue)
        }

        return JsonObject(elements)
    }

    fun JsonArray.merge(other: JsonArray, options: JsonMergeOptions = JsonMergeOptions()): JsonArray = when (options.arrayHandling) {
        ArrayMergeHandling.CONCAT -> concat(other)
        ArrayMergeHandling.UNION -> union(other)
        ArrayMergeHandling.REPLACE -> other
        ArrayMergeHandling.MERGE -> replaceByIndex(other)
    }

    private fun JsonArray.concat(other: JsonArray): JsonArray {
        val elements = plus(other)
        return JsonArray(elements)
    }

    private fun JsonArray.union(other: JsonArray): JsonArray {
        val elements = plus(other).distinct()
        return JsonArray(elements)
    }

    private fun JsonArray.replaceByIndex(other: JsonArray): JsonArray {
        val elements = mutableListOf<JsonElement>()

        for (index in 0 until max(size, other.size)) {
            val thisElement = getOrNull(index) ?: JsonNull
            val otherElement = other.getOrNull(index)
            val mergedElement = thisElement.merge(otherElement)
            elements.add(mergedElement)
        }

        return JsonArray(elements)
    }
}