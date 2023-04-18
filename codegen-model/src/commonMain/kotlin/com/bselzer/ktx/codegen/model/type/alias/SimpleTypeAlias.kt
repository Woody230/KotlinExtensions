package com.bselzer.ktx.codegen.model.type.alias

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class SimpleTypeAlias(
    override val type: TypeName,
    override val name: String,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val modifiers: Set<TypeAliasModifier> = setOf(),
    override val typeVariables: Collection<TypeVariableName> = emptyList()
) : TypeAlias