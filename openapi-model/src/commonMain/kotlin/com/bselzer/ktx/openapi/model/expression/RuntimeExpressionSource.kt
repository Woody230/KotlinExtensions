package com.bselzer.ktx.openapi.model.expression

import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier
import com.bselzer.ktx.openapi.model.expression.reference.*

class RuntimeExpressionSource(value: String) {
    val reference: RuntimeExpressionReference = when {
        value.startsWith("header.") -> RuntimeExpressionHeaderReference(value.removePrefix("header."))
        value.startsWith("query.") -> RuntimeExpressionQueryReference(value.removePrefix("query."))
        value.startsWith("path.") -> RuntimeExpressionPathReference(value.removePrefix("path."))
        value.startsWith("body") -> RuntimeExpressionBodyReference(OpenApiReferenceIdentifier(value.removePrefix("body")))
        else -> RuntimeExpressionUnknownReference(value)
    }
}