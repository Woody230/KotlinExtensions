package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiLong(private val value: Long) : OpenApiNumber {
    fun toLong(): Long = value
    override fun toNumber(): Number = value
}