package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiFloat(private val value: Float) : OpenApiNumber {
    override fun toString(): String = value.toString()
    fun toFloat(): Float = value
    override fun toNumber(): Number = value
}