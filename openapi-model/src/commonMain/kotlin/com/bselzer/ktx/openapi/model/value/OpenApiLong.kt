package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiLong(private val value: Long) : OpenApiNumber {
    override fun toNumber(): Number = value
}