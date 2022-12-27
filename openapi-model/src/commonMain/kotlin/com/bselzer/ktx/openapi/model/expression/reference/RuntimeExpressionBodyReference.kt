package com.bselzer.ktx.openapi.model.expression.reference

import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePath

/**
 * In operations which accept payloads, references may be made to portions of the requestBody or the entire body.
 */
data class RuntimeExpressionBodyReference(val pointer: OpenApiReferencePath) : RuntimeExpressionReference