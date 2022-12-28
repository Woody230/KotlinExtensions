package com.bselzer.ktx.openapi.model.value

interface OpenApiNumber : OpenApiValue {
    fun toNumber(): Number
    fun toDouble(): Double = toNumber().toDouble()
    fun toFloat(): Float = toNumber().toFloat()
    fun toInt(): Int = toNumber().toInt()
    fun toLong(): Long = toNumber().toLong()
}