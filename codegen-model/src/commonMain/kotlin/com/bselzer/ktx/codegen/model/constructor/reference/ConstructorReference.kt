package com.bselzer.ktx.codegen.model.constructor.reference

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

sealed interface ConstructorReference {
    val type: ConstructorReferenceType
    val arguments: Collection<CodeBlock>
}