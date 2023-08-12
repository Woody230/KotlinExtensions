package com.bselzer.ktx.serialization.merge

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlin.math.max

class JsonMerger(
    private val options: JsonMergeOptions
) {
    fun JsonElement.merge(
        other: JsonElement?,
    ): JsonElement = when {
        other is JsonNull || other == null -> mergeNull()
        this is JsonObject && other is JsonObject -> merge(other)
        this is JsonArray && other is JsonArray -> merge(other)
        else -> other
    }

    private fun JsonElement.mergeNull() = when (options.nullHandling) {
        NullMergeHandling.IGNORE -> this
        NullMergeHandling.MERGE -> JsonNull
    }

    fun JsonObject.merge(other: JsonObject): JsonObject {
        val keys = keys + other.keys
        val elements = mutableMapOf<String, JsonElement>()
        for (key in keys) {
            val thisValue = this[key] ?: JsonNull
            val otherValue = other[key]
            elements[key] = thisValue.merge(otherValue)
        }

        return JsonObject(elements)
    }

    fun JsonArray.merge(other: JsonArray): JsonArray = when (options.arrayHandling) {
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