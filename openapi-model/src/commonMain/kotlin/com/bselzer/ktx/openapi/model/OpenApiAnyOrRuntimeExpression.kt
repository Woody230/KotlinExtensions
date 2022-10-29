package com.bselzer.ktx.openapi.model

// TODO any or expression
data class OpenApiAnyOrRuntimeExpression(
    val value: Any? = null,
    val expression: OpenApiRuntimeExpression? = null
)