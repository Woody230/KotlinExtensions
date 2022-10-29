package com.bselzer.ktx.openapi.model

data class OpenApiAnyOrRuntimeExpression(
    val value: Any? = null,
    val expression: OpenApiRuntimeExpression? = null
)