package com.bselzer.ktx.openapi.model.schema

import kotlin.jvm.JvmInline

@JvmInline
@kotlinx.serialization.Serializable
value class OpenApiPropertyName(private val value: String) {
    override fun toString(): String = value
}