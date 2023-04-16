package com.bselzer.ktx.codegen.model.type.`class`.enum

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.parameter.Parameter

// TODO implement constructor
interface EnumConstructor {
    val parameters: Collection<Parameter>
    val body: CodeBlock?
}