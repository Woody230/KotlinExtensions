package com.bselzer.ktx.codegen.model.parameter

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.name.TypeName

data class Parameter(
    val type: TypeName,
    val name: String,
    val defaultValue: CodeBlock? = null,
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val modifiers: Set<ParameterModifier> = emptySet(),
) {
    override fun toString(): String = name
}