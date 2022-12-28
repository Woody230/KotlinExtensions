package com.bselzer.ktx.openapi.model.expression

class ResponseRuntimeExpression internal constructor(
    private val expression: String
) : OpenApiRuntimeExpression {
    val source: RuntimeExpressionSource = RuntimeExpressionSource(expression.removePrefix("\$response."))

    override fun toString(): String = expression
}