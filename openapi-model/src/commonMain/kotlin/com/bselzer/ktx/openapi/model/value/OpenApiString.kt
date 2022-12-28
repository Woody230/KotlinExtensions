package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiString(private val value: String) : OpenApiValue {
    override fun toString(): String = value
}