package com.bselzer.ktx.codegen.model.constructor.reference

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

class ThisConstructor(
    override val arguments: Collection<CodeBlock>
) : ConstructorReference {
    override val type: ConstructorReferenceType = ConstructorReferenceType.THIS

    override fun toString(): String = "this(${arguments.joinToString { "*" }})"
}