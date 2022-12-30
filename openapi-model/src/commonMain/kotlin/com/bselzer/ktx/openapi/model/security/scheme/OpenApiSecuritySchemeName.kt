package com.bselzer.ktx.openapi.model.security.scheme

import kotlin.jvm.JvmInline

@JvmInline
@kotlinx.serialization.Serializable
value class OpenApiSecuritySchemeName(private val value: String) {
    override fun toString(): String = value
}