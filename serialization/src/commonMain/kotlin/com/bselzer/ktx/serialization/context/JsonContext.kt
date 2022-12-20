package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

sealed class JsonContext(
    override val instance: Json
) : StringFormatContext(instance) {
    companion object Default : JsonContext(Json)

    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromJsonElement(deserializer, JsonPrimitive(this))

    fun JsonElement.merge(other: JsonElement, arrayHandling: ArrayMergeHandling = ArrayMergeHandling.MERGE): JsonElement = when {
        this is JsonObject && other is JsonObject -> merge(other)
        this is JsonArray && other is JsonArray -> merge(other, arrayHandling)
        other is JsonNull -> this
        else -> other
    }

    private fun JsonObject.merge(other: JsonObject): JsonObject {
        val elements = plus(other.filterValues { element -> element !is JsonNull })
        return JsonObject(elements)
    }

    fun JsonArray.merge(other: JsonArray, handling: ArrayMergeHandling = ArrayMergeHandling.MERGE): JsonArray = when (handling) {
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
        val elements = zip(other).map { elements ->
            if (elements.second is JsonNull) {
                elements.first
            } else {
                elements.second
            }
        }

        return JsonArray(elements)
    }
}