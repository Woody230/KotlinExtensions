package com.bselzer.ktx.codegen.model.type.`super`.`interface`

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.type.name.TypeName

data class SimpleDelegableSuperInterface(
    override val type: TypeName,
    override val delegate: CodeBlock? = null,
) : DelegableSuperInterface