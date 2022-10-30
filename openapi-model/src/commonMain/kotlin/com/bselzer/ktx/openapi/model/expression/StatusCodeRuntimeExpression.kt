package com.bselzer.ktx.openapi.model.expression

/**
 * In operations which return payloads, references may be made to portions of the response body or the entire body.
 */
object StatusCodeRuntimeExpression : OpenApiRuntimeExpression {
    override val value: String = "\$statusCode"
}