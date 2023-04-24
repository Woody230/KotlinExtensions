package com.bselzer.ktx.codegen.model.function

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class Function(
    val name: String,
    val parameters: Collection<Parameter> = emptyList(),
    val returns: TypeName? = null,
    val body: CodeBlock? = null,
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val modifiers: Set<FunctionModifier> = emptySet(),
    val typeVariables: Collection<TypeVariableName> = emptyList(),
    val receiver: TypeName? = null,
    val contextReceivers: Collection<TypeName> = emptyList()
) {
    override fun toString(): String = name

    companion object {
        fun toStringOf(body: CodeBlock): Function = Function(
            name = "toString",
            returns = ClassName.STRING,
            modifiers = setOf(FunctionModifier.OVERRIDE),
            body = body
        )
    }
}