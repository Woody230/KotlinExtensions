package com.bselzer.ktx.codegen.model.type.alias

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class TypeAlias(
    val type: TypeName,
    val name: String,
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val modifiers: Set<TypeAliasModifier> = setOf(),
    val typeVariables: Collection<TypeVariableName> = emptyList()
) {
    override fun toString(): String = "$type as $name"
}