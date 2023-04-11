package com.bselzer.ktx.codegen.model.function

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation

interface Function {
    val name: String
    val documentation: Documentation?
    val annotations: Collection<Annotation>
}