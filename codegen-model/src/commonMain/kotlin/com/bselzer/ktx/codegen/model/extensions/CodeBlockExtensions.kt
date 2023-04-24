package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.codeblock.LiteralCodeBlock
import com.bselzer.ktx.codegen.model.codeblock.QuotedCodeBlock

fun String.toCodeBlock(): CodeBlock = LiteralCodeBlock(this)
fun String.toQuotedCodeBlock(): CodeBlock = QuotedCodeBlock(this)