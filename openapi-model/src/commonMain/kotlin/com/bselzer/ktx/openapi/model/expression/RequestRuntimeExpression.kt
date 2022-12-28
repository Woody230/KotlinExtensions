package com.bselzer.ktx.openapi.model.expression

class RequestRuntimeExpression internal constructor(
    private val expression: String
) : OpenApiRuntimeExpression {
    val source: RuntimeExpressionSource = RuntimeExpressionSource(expression.removePrefix("\$request."))

    override fun toString(): String = expression
}