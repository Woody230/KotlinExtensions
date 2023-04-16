package com.bselzer.ktx.codegen.model.function

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class SimpleFunction(
    override val name: String,
    override val parameters: Collection<Parameter> = emptyList(),
    override val returns: TypeName? = null,
    override val body: CodeBlock? = null,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val modifiers: Set<FunctionModifier> = emptySet(),
    override val typeVariables: Collection<TypeVariableName> = emptyList(),
    override val receiver: TypeName? = null,
    override val contextReceivers: Collection<TypeName> = emptyList()
) : Function