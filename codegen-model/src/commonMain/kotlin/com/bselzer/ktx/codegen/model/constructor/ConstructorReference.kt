package com.bselzer.ktx.codegen.model.constructor

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

interface ConstructorReference {
    val type: ConstructorReferenceType
    val arguments: Collection<CodeBlock>
}