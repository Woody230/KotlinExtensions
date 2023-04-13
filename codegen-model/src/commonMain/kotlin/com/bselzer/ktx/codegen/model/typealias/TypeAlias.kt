package com.bselzer.ktx.codegen.model.`typealias`

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.codegen.model.type.TypeVariableName

interface TypeAlias {
    val name: String
    val type: TypeName
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val modifiers: Collection<TypeAliasModifier>
    val typeVariables: Collection<TypeVariableName>
}