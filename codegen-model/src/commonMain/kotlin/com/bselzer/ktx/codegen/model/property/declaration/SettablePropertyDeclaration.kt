package com.bselzer.ktx.codegen.model.property.declaration

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.function.Function

data class SettablePropertyDeclaration(
    val getter: Function,
    val setter: Function,
    val initializer: CodeBlock? = null
) : PropertyDeclaration {
    override val mutable: Boolean = true
}