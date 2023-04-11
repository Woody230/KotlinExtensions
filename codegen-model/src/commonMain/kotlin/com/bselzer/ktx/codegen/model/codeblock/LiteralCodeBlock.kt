package com.bselzer.ktx.codegen.model.codeblock

import kotlin.jvm.JvmInline

@JvmInline
value class LiteralCodeBlock(private val value: String) : CodeBlock {
    override fun toString(): String = value
}