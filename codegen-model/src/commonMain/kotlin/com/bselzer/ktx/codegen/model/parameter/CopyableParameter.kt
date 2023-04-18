package com.bselzer.ktx.codegen.model.parameter

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.name.TypeName

data class CopyableParameter(
    override val type: TypeName,
    override val name: String,
    override val defaultValue: CodeBlock? = null,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val modifiers: Set<ParameterModifier> = emptySet(),
) : Parameter