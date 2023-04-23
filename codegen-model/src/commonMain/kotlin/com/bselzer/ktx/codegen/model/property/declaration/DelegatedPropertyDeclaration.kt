package com.bselzer.ktx.codegen.model.property.declaration

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

data class DelegatedPropertyDeclaration(
    override val mutable: Boolean,
    val delegate: CodeBlock
) : PropertyDeclaration