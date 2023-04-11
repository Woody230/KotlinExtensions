package com.bselzer.ktx.codegen.model.parameter

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.TypeName

interface Parameter {
    val name: String
    val typeName: TypeName
    val documentation: Documentation?
    val annotations: Collection<Annotation>
}