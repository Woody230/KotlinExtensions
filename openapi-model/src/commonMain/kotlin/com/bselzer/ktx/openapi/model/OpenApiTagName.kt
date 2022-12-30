package com.bselzer.ktx.openapi.model

import kotlin.jvm.JvmInline

@JvmInline
@kotlinx.serialization.Serializable
value class OpenApiTagName(private val value: String) {
    override fun toString(): String = value
}