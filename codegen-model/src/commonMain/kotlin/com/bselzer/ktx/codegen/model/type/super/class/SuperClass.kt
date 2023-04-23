package com.bselzer.ktx.codegen.model.type.`super`.`class`

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.type.name.TypeName

data class SuperClass(
    val type: TypeName,
    val arguments: Collection<CodeBlock> = emptyList()
) {
    override fun toString(): String = type.toString()
}