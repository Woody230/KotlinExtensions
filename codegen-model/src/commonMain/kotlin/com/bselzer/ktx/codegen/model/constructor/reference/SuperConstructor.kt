package com.bselzer.ktx.codegen.model.constructor.reference

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

data class SuperConstructor(
    override val arguments: Collection<CodeBlock>
) : ConstructorReference {
    override val type: ConstructorReferenceType = ConstructorReferenceType.SUPER

    override fun toString(): String = "super(${arguments.joinToString { "*" }})"
}