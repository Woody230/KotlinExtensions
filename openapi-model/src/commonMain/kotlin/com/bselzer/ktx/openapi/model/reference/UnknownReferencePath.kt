package com.bselzer.ktx.openapi.model.reference

import kotlin.jvm.JvmInline

@JvmInline
value class UnknownReferencePath internal constructor(private val value: String) : OpenApiReferencePath {
    override fun toString(): String = value
}