package com.bselzer.ktx.openapi.model.reference

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiReferencePath(private val value: String) {
    override fun toString(): String = value
}