package com.bselzer.ktx.openapi.model.value

interface OpenApiNumber : OpenApiValue {
    fun toNumber(): Number
}