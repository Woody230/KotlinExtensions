package com.bselzer.ktx.serialization.context

import com.bselzer.ktx.serialization.merge.JsonMergeOptions
import com.bselzer.ktx.serialization.merge.JsonMerger
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

open class JsonContext(
    override val instance: Json
) : StringFormatContext(instance) {
    companion object Default : JsonContext(Json)

    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromJsonElement(deserializer, JsonPrimitive(this))

    fun JsonElement.merge(
        other: JsonElement?,
        options: JsonMergeOptions = JsonMergeOptions.Default
    ): JsonElement = JsonMerger(options).run { merge(other) }

    fun JsonObject.merge(
        other: JsonObject,
        options: JsonMergeOptions = JsonMergeOptions.Default
    ): JsonObject = JsonMerger(options).run { merge(other) }

    fun JsonArray.merge(
        other: JsonArray,
        options: JsonMergeOptions = JsonMergeOptions.Default
    ): JsonArray = JsonMerger(options).run { merge(other) }
}