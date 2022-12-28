package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiInt(private val value: Int) : OpenApiNumber {
    override fun toString(): String = value.toString()
    fun toInt(): Int = value
    override fun toNumber(): Number = value
}