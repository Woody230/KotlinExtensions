package com.bselzer.ktx.codegen.model.type.`super`.`interface`

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.type.name.TypeName

data class SuperInterface(
    val type: TypeName,
    val delegate: CodeBlock? = null
) {
    override fun toString(): String = type.toString()
}