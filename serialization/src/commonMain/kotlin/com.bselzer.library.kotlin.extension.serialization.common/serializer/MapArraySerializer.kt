package com.bselzer.library.kotlin.extension.serialization.common.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * The serializer for converting a json array of json arrays into a map.
 */
open class MapArraySerializer<T>(valueSerializer: KSerializer<T>) : KSerializer<Map<T, T>>
{
    /**
     * The list serializer.
     */
    private val listSerializer = ListSerializer(ListSerializer(valueSerializer))

    override val descriptor = listSerializer.descriptor

    override fun deserialize(decoder: Decoder): Map<T, T>
    {
        val lists = listSerializer.deserialize(decoder)
        return lists.map { list -> list[0] to list[1] }.toMap()
    }

    override fun serialize(encoder: Encoder, value: Map<T, T>)
    {
        val lists = value.map { kvp -> listOf(kvp.key, kvp.value) }
        encoder.encodeSerializableValue(listSerializer, lists)
    }
}