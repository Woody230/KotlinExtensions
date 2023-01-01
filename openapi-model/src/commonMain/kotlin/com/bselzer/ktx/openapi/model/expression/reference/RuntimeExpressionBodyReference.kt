package com.bselzer.ktx.openapi.model.expression.reference

/**
 * In operations which accept payloads, references may be made to portions of the requestBody or the entire body.
 */
data class RuntimeExpressionBodyReference(val pointer: String) : RuntimeExpressionReference