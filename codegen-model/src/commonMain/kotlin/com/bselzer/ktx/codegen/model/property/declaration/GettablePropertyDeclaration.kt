package com.bselzer.ktx.codegen.model.property.declaration

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.function.Function

data class GettablePropertyDeclaration(
    val getter: Function,
    val initializer: CodeBlock? = null
) : AccessorPropertyDelegation {
    override val mutable: Boolean = false
}