package com.bselzer.ktx.codegen.model.constructor

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.parameter.Parameter

data class Constructor(
    val parameters: Collection<Parameter> = emptyList(),
    val modifiers: Set<ConstructorModifier> = emptySet(),
    val body: CodeBlock? = null,
    val reference: ConstructorReference? = null
) {
    override fun toString(): String = "constructor(${parameters.joinToString { "*" }})"
}