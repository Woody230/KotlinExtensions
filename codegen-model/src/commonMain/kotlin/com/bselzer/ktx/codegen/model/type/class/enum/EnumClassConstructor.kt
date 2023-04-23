package com.bselzer.ktx.codegen.model.type.`class`.enum

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.parameter.Parameter

class EnumClassConstructor(
    override val parameters: Collection<Parameter>,
    override val body: CodeBlock? = null
) : Constructor {
    override val reference: ConstructorReference?
        get() = null

    override val modifiers: Set<ConstructorModifier>
        get() = emptySet()

    override fun toString(): String = "constructor(${parameters.joinToString { "*" }})"
}