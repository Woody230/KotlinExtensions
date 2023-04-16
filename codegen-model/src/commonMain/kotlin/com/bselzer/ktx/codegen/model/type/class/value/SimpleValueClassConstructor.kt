package com.bselzer.ktx.codegen.model.type.`class`.value

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.parameter.Parameter

data class SimpleValueClassConstructor(
    override val parameter: Parameter,
    override val modifiers: Set<ConstructorModifier> = emptySet(),
    override val body: CodeBlock? = null,
    override val reference: ConstructorReference? = null,
) : ValueClassConstructor