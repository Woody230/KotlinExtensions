package com.bselzer.ktx.openapi.model.expression

/**
 * The allowable values for the $method will be those for the HTTP operation.
 */
object MethodRuntimeExpression : OpenApiRuntimeExpression {
    override val value: String = "\$method"
}