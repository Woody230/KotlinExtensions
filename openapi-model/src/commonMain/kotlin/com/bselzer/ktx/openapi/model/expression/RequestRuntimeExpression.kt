package com.bselzer.ktx.openapi.model.expression

class RequestRuntimeExpression(override val value: String) : OpenApiRuntimeExpression {
    val source: RuntimeExpressionSource = RuntimeExpressionSource(value.removePrefix("\$request."))
}