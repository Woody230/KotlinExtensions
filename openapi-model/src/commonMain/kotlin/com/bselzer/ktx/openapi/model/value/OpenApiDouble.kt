package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiDouble(private val value: Double) : OpenApiNumber {
    override fun toString(): String = value.toString()
    fun toDouble(): Double = value
    override fun toNumber(): Number = value
}