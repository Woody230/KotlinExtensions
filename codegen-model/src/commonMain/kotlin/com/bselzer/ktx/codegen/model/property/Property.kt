package com.bselzer.ktx.codegen.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.property.declaration.PropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class Property(
    val type: TypeName,
    val name: String,
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val declaration: PropertyDeclaration? = null,
    val receiver: TypeName? = null,
    val contextReceivers: Collection<TypeName> = emptyList(),
    val typeVariables: Collection<TypeVariableName> = emptyList(),
    val modifiers: Set<PropertyModifier> = emptySet()
) {
    override fun toString(): String = name
}