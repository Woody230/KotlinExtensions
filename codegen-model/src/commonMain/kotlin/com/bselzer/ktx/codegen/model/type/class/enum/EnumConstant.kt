package com.bselzer.ktx.codegen.model.type.`class`.enum

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation

interface EnumConstant {
    val name: String
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val arguments: Collection<CodeBlock>
}