package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiBoolean(private val value: Boolean) : OpenApiValue {
    override fun toString(): String = value.toString()
    fun toBoolean(): Boolean = value
}