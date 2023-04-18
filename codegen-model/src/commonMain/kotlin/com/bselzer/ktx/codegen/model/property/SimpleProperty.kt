package com.bselzer.ktx.codegen.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.property.declaration.PropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

data class SimpleProperty(
    override val type: TypeName,
    override val name: String,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val declaration: PropertyDeclaration? = null,
    override val receiver: TypeName? = null,
    override val contextReceivers: Collection<TypeName> = emptyList(),
    override val typeVariables: Collection<TypeVariableName> = emptyList(),
    override val modifiers: Set<PropertyModifier> = emptySet()
) : Property