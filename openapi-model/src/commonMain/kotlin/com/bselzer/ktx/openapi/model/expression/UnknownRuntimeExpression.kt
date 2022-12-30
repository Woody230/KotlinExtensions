package com.bselzer.ktx.openapi.model.expression

import kotlin.jvm.JvmInline

@JvmInline
value class UnknownRuntimeExpression internal constructor(private val value: String) : OpenApiRuntimeExpression {
    override fun toString(): String = value
}