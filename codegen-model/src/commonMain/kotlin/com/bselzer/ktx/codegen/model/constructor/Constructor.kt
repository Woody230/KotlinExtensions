package com.bselzer.ktx.codegen.model.constructor

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.FunctionModifier
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.type.TypeVariableName

interface Constructor {
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val parameters: Collection<Parameter>
    val modifiers: Set<FunctionModifier>
    val typeVariables: Collection<TypeVariableName>
    val body: CodeBlock
    val reference: ConstructorReference?
}