package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.codeblock.LiteralCodeBlock

fun String.toCodeBlock(): CodeBlock = LiteralCodeBlock(this)