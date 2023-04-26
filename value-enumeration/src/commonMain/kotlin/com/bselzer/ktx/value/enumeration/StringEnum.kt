package com.bselzer.ktx.value.enumeration

import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeOrNull
import com.bselzer.ktx.serialization.serializer.StringEnumSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable(with = StringEnumSerializer::class)
class StringEnum<T> @PublishedApi internal constructor(
    private val value: String,
    private val serializer: KSerializer<T>
) : BindableEnum<T> where T : Enum<T> {
    override fun toEnum(): T = value.decode(serializer)
    override fun toEnumOrNull(): T? = value.decodeOrNull(serializer)
    override fun toString(): String = value
}