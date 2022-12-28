package com.bselzer.ktx.openapi.model.security.scheme

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiSecuritySchemeName(private val value: String) {
    override fun toString(): String = value
}