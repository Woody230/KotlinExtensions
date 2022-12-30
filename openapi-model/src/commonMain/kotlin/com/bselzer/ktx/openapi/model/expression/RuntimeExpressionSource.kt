package com.bselzer.ktx.openapi.model.expression

import com.bselzer.ktx.openapi.model.expression.reference.*
import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePathFactory

class RuntimeExpressionSource internal constructor(value: String) {
    val reference: RuntimeExpressionReference = when {
        value.startsWith("header.") -> RuntimeExpressionHeaderReference(value.removePrefix("header."))
        value.startsWith("query.") -> RuntimeExpressionQueryReference(value.removePrefix("query."))
        value.startsWith("path.") -> RuntimeExpressionPathReference(value.removePrefix("path."))
        value.startsWith("body") -> run {
            val pointer = OpenApiReferencePathFactory(value.removePrefix("body")).referencePath
            RuntimeExpressionBodyReference(pointer)
        }
        else -> RuntimeExpressionUnknownReference(value)
    }
}