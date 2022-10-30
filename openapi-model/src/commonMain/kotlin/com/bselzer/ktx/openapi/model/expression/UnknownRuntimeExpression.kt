package com.bselzer.ktx.openapi.model.expression

import kotlin.jvm.JvmInline

@JvmInline
value class UnknownRuntimeExpression(override val value: String) : OpenApiRuntimeExpression