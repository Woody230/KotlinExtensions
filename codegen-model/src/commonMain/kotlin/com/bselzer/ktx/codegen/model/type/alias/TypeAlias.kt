package com.bselzer.ktx.codegen.model.type.alias

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName

interface TypeAlias {
    val name: String
    val type: TypeName
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val modifiers: Set<TypeAliasModifier>
    val typeVariables: Collection<TypeVariableName>
}