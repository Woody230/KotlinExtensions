package com.bselzer.ktx.codegen.model.codeblock

import kotlin.jvm.JvmInline

@JvmInline
value class QuotedCodeBlock(private val value: String) : CodeBlock {
    override fun toString(): String = "\"$value\""

    companion object {
        val Empty: QuotedCodeBlock = QuotedCodeBlock("")
    }
}