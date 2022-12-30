package com.bselzer.ktx.openapi.model.path

import kotlin.jvm.JvmInline

@JvmInline
@kotlinx.serialization.Serializable
value class OpenApiMediaTypeName(private val value: String) {
    override fun toString(): String = value
}