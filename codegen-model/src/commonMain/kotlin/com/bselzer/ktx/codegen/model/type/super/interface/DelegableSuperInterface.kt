package com.bselzer.ktx.codegen.model.type.`super`.`interface`

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

interface DelegableSuperInterface : SuperInterface {
    val delegate: CodeBlock?
}