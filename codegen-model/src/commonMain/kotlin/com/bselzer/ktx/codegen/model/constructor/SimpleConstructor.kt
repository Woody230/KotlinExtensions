package com.bselzer.ktx.codegen.model.constructor

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.parameter.Parameter

data class SimpleConstructor(
    override val parameters: Collection<Parameter>,
    override val modifiers: Set<ConstructorModifier> = emptySet(),
    override val body: CodeBlock? = null,
    override val reference: ConstructorReference? = null
) : Constructor