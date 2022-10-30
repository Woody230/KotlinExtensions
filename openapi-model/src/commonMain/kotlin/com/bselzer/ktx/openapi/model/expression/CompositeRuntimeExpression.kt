package com.bselzer.ktx.openapi.model.expression

/**
 * Contains embedded expressions. For example, 'http://notificationServer.com?transactionId={$request.body#/id}&email={$request.body#/email}' contains two expressions.
 */
// Related: https://github.com/microsoft/OpenAPI.NET/issues/287
class CompositeRuntimeExpression(override val value: String) : OpenApiRuntimeExpression {
    /**
     * The sub-expressions contained within the [value].
     */
    val subExpressions: Sequence<OpenApiRuntimeExpression> = run {
        val groups = Regex("(?<expression>\$[^}]*)").findAll(value).map { match -> match.groups }
        val expressions = groups.mapNotNull { group -> group.firstOrNull()?.value }
        expressions.map { value -> RuntimeExpressionFactory(value) }
    }
}