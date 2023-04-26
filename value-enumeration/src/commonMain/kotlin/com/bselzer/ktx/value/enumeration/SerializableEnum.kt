package com.bselzer.ktx.value.enumeration

import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeOrNull
import com.bselzer.ktx.serialization.serializer.SerializableEnumSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable(with = SerializableEnumSerializer::class)
class SerializableEnum<T> @PublishedApi internal constructor(
    private val value: String,
    private val serializer: KSerializer<T>
) : BindableEnum<T> where T : Enum<T> {
    override fun toEnum(): T = value.decode(serializer)
    override fun toEnumOrNull(): T? = value.decodeOrNull(serializer)
    override fun toString(): String = value
}