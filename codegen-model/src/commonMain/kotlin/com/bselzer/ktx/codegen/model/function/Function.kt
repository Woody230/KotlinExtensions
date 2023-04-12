package com.bselzer.ktx.codegen.model.function

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.codegen.model.type.TypeVariableName

interface Function {
    val name: String
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val parameters: Collection<Parameter>
    val modifiers: Collection<FunctionModifier>
    val typeVariables: Collection<TypeVariableName>
    val receiver: TypeName?
    val contextReceivers: Collection<TypeName>
    val returns: TypeName?
    val body: CodeBlock
}