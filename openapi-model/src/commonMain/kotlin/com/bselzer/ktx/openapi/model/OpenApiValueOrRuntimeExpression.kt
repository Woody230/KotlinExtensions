package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.expression.OpenApiRuntimeExpression
import com.bselzer.ktx.openapi.model.value.OpenApiValue

class OpenApiValueOrRuntimeExpression {
    @PublishedApi
    internal val value: Any?

    /**
     * Initializes a new instance of the [OpenApiValueOrRuntimeExpression] class.
     *
     * @param value the value
     */
    constructor(value: OpenApiValue) {
        this.value = value
    }

    /**
     * Initializes a new instance of the [OpenApiValueOrRuntimeExpression] class.
     *
     * @param expression the expression
     */
    constructor(expression: OpenApiRuntimeExpression) {
        this.value = expression
    }

    /**
     * Resolve the cases where the value is either of type [T], or is a reference.
     */
    inline fun resolve(
        onValue: (OpenApiValue) -> Unit,
        onExpression: (OpenApiReference) -> Unit
    ) = when (value) {
        is OpenApiValue -> onValue(value)
        is OpenApiReference -> onExpression(value)
        else -> {}
    }
}