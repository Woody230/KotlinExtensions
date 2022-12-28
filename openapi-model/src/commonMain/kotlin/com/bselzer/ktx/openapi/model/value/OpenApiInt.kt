package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiInt(private val value: Int) : OpenApiNumber {
    override fun toString(): String = value.toString()
    override fun toNumber(): Number = value
}