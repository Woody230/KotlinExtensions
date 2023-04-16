package com.bselzer.ktx.codegen.model.type.`class`.enum

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.parameter.Parameter

data class SimpleEnumClassConstructor(
    override val parameters: Collection<Parameter>,
    override val body: CodeBlock? = null
) : EnumClassConstructor