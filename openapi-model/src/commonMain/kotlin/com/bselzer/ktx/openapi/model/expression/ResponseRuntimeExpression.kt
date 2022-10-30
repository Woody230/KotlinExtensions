package com.bselzer.ktx.openapi.model.expression

class ResponseRuntimeExpression(override val value: String) : OpenApiRuntimeExpression {
    val source: RuntimeExpressionSource = RuntimeExpressionSource(value.removePrefix("\$response."))
}