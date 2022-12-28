package com.bselzer.ktx.openapi.model.path

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiEncodingName(private val value: String) {
    override fun toString(): String = value
}