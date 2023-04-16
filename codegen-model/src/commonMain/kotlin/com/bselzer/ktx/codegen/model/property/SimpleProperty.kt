package com.bselzer.ktx.codegen.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class SimpleProperty(
    override val type: TypeName,
    override val name: String,
    override val mutable: Boolean = false,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val initializer: CodeBlock? = null,
    override val delegated: CodeBlock? = null,
    override val getter: Function? = null,
    override val setter: Function? = null,
    override val receiver: TypeName? = null,
    override val contextReceivers: Collection<TypeName> = emptyList(),
    override val typeVariables: Collection<TypeVariableName> = emptyList(),
    override val modifiers: Set<PropertyModifier> = emptySet()
) : Property