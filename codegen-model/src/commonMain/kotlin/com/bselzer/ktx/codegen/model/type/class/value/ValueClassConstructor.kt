package com.bselzer.ktx.codegen.model.type.`class`.value

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.parameter.Parameter

class ValueClassConstructor(
    val parameter: Parameter,
    override val modifiers: Set<ConstructorModifier> = emptySet(),
    override val body: CodeBlock? = null,
    override val reference: ConstructorReference? = null
) : Constructor {
    override val parameters: Collection<Parameter>
        get() = listOf(parameter)

    override fun toString(): String = "constructor(${parameters.joinToString { "*" }})"
}