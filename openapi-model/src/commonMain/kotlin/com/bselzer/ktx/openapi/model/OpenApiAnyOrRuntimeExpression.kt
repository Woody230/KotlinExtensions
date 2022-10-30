package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.expression.OpenApiRuntimeExpression

// TODO any or expression
data class OpenApiAnyOrRuntimeExpression(
    val value: Any? = null,
    val expression: OpenApiRuntimeExpression? = null
)