package com.bselzer.ktx.codegen.model.type.`super`.`class`

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.type.name.TypeName

interface SuperClass {
    val type: TypeName
    val arguments: Collection<CodeBlock>
}