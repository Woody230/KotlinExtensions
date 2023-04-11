package com.bselzer.ktx.codegen.model.documentation

import kotlin.jvm.JvmInline

@JvmInline
value class LiteralDocumentation(private val value: String) {
    override fun toString(): String = value
}