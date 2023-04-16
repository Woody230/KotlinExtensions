package com.bselzer.ktx.codegen.model.constructor

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.parameter.Parameter

interface Constructor {
    val parameters: Collection<Parameter>
    val modifiers: Set<ConstructorModifier>
    val body: CodeBlock?
    val reference: ConstructorReference?
}