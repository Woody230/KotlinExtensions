package com.bselzer.ktx.codegen.model.type.`super`.`class`

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.type.name.TypeName

data class SimpleSuperClass(
    override val type: TypeName,
    override val arguments: Collection<CodeBlock> = emptyList()
) : SuperClass