package com.bselzer.ktx.openapi.model.expression

/**
 * Contains embedded expressions. For example, 'http://notificationServer.com?transactionId={$request.body#/id}&email={$request.body#/email}' contains two expressions.
 */
// Related: https://github.com/microsoft/OpenAPI.NET/issues/287
class CompositeRuntimeExpression internal constructor(
    private val expression: String
) : OpenApiRuntimeExpression {
    /**
     * The sub-expressions contained within the [expression].
     */
    val subExpressions: Sequence<OpenApiRuntimeExpression> = run {
        val groups = Regex("(?<expression>\$[^}]*)").findAll(expression).map { match -> match.groups }
        val expressions = groups.mapNotNull { group -> group.firstOrNull()?.value }
        expressions.map(String::toOpenApiRuntimeExpression)
    }

    override fun toString(): String = expression
}