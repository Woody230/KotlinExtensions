package com.bselzer.ktx.openapi.model

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiDescription(private val value: String) {
    override fun toString(): String = value
}