package com.bselzer.ktx.openapi.model.expression.reference

import kotlin.jvm.JvmInline

@JvmInline
value class RuntimeExpressionUnknownReference(private val value: String) : RuntimeExpressionReference {
    override fun toString(): String = value
}