package com.bselzer.ktx.codegen.model.type

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock

interface DelegableSuperInterface : SuperInterface {
    val delegate: CodeBlock?
}