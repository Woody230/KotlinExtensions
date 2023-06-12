package com.bselzer.ktx.serialization.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * The serializer for converting an array of arrays into a map.
 */
// TODO https://github.com/Kotlin/kotlinx.serialization/issues/1587 Despite not needing a second generic parameter, it is required for the plugin to work.
open class MapArraySerializer<Key, Value>(keySerializer: KSerializer<Key>, valueSerializer: KSerializer<Value>) :
    KSerializer<Map<Key, Key>>
{
    /**
     * The list serializer.
     */
    private val listSerializer = ListSerializer(ListSerializer(keySerializer))

    override val descriptor = listSerializer.descriptor

    override fun deserialize(decoder: Decoder): Map<Key, Key>
    {
        val lists = listSerializer.deserialize(decoder)
        return lists.associate { list -> list[0] to list[1] }
    }

    override fun serialize(encoder: Encoder, value: Map<Key, Key>)
    {
        val lists = value.map { kvp -> listOf(kvp.key, kvp.value) }
        encoder.encodeSerializableValue(listSerializer, lists)
    }
}