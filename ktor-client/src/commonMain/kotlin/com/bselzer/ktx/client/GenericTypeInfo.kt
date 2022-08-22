package com.bselzer.ktx.client

import io.ktor.util.reflect.*
import kotlin.jvm.JvmInline

/**
 * A wrapper for enforcing the type associated with the reified usage of the [typeInfo] method.
 */
@JvmInline
@Suppress("UNUSED")
value class GenericTypeInfo<Model> @PublishedApi internal constructor(val value: TypeInfo) {
    override fun toString(): String = value.toString()

    fun toDisplayableString() = with(value) { kotlinType ?: type.simpleName }
}

inline fun <reified Model> genericTypeInfo(): GenericTypeInfo<Model> = GenericTypeInfo(typeInfo<Model>())