package com.bselzer.ktx.openapi.model.expression

import kotlin.jvm.JvmInline

@JvmInline
value class UnknownRuntimeExpression internal constructor(private val expression: String) : OpenApiRuntimeExpression {
    override fun toString(): String = expression
}