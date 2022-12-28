package com.bselzer.ktx.openapi.model.parameter

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiParameterName(private val value: String) {
    override fun toString(): String = value
}