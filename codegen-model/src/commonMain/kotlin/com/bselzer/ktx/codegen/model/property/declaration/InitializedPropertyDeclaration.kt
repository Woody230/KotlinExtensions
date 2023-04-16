package com.bselzer.ktx.codegen.model.property.declaration

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

data class InitializedPropertyDeclaration(
    override val mutable: Boolean,
    val initializer: CodeBlock
) : PropertyDeclaration