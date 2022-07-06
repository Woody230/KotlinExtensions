package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive

sealed class JsonContext(
    override val instance: Json
) : StringFormatContext(instance) {
    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromJsonElement(deserializer, JsonPrimitive(this))

    companion object Default : JsonContext(Json)
}