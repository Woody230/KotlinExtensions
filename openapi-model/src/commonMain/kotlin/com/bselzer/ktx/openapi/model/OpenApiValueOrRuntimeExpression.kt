package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.expression.OpenApiRuntimeExpression
import com.bselzer.ktx.openapi.model.value.OpenApiValue

class OpenApiValueOrRuntimeExpression {
    @PublishedApi
    internal val value: OpenApiValue?

    @PublishedApi
    internal val expression: OpenApiRuntimeExpression?

    /**
     * Initializes a new instance of the [OpenApiValueOrRuntimeExpression] class.
     *
     * @param value the value
     */
    constructor(value: OpenApiValue) {
        this.value = value
        this.expression = null
    }

    /**
     * Initializes a new instance of the [OpenApiValueOrRuntimeExpression] class.
     *
     * @param expression the expression
     */
    constructor(expression: OpenApiRuntimeExpression) {
        this.value = null
        this.expression = expression
    }

    /**
     * Resolve the cases where the value is either an [OpenApiValue], or an expression
     */
    fun resolve(
        onValue: (OpenApiValue) -> Unit,
        onExpression: (OpenApiRuntimeExpression) -> Unit
    ) {
        if (value != null) {
            onValue(value)
        } else if (expression != null) {
            onExpression(expression)
        }
    }
}