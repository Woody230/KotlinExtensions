package com.bselzer.ktx.openapi.model.security.scheme

import kotlin.jvm.JvmInline

@JvmInline
@kotlinx.serialization.Serializable
value class OpenApiSecurityScope(private val value: String) {
    override fun toString(): String = value
}