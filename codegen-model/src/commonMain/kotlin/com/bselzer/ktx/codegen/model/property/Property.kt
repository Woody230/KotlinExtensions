package com.bselzer.ktx.codegen.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.property.declaration.PropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

interface Property {
    val type: TypeName
    val name: String
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val declaration: PropertyDeclaration?
    val receiver: TypeName?
    val contextReceivers: Collection<TypeName>
    val typeVariables: Collection<TypeVariableName>
    val modifiers: Set<PropertyModifier>
}