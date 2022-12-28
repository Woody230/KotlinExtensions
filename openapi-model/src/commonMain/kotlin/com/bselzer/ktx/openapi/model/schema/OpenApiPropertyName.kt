package com.bselzer.ktx.openapi.model.schema

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiPropertyName(private val value: String) {
    override fun toString(): String = value
}