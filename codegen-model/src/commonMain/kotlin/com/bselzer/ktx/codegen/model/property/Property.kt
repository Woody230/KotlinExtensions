package com.bselzer.ktx.codegen.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.codegen.model.type.TypeVariableName

interface Property {
    val type: TypeName
    val name: String
    val mutable: Boolean
    val description: Documentation?
    val annotations: Collection<Annotation>
    val initializer: CodeBlock?
    val delegated: CodeBlock?
    val getter: Function?
    val setter: Function?
    val receiver: TypeName?
    val contextReceivers: Collection<TypeName>
    val typeVariables: Collection<TypeVariableName>
    val modifiers: Collection<PropertyModifier>
}