package com.bselzer.ktx.openapi.model.expression

class RuntimeExpressionFactory(value: String) {
    val expression: OpenApiRuntimeExpression = when {
        value.isBlank() -> UnknownRuntimeExpression(value)
        !value.startsWith('$') -> CompositeRuntimeExpression(value)
        value == UrlRuntimeExpression.value -> UrlRuntimeExpression
        value == MethodRuntimeExpression.value -> MethodRuntimeExpression
        value == StatusCodeRuntimeExpression.value -> StatusCodeRuntimeExpression
        value.startsWith("\$request.") -> RequestRuntimeExpression(value)
        value.startsWith("\$response.") -> ResponseRuntimeExpression(value)
        else -> UnknownRuntimeExpression(value)
    }
}