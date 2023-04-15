package com.bselzer.ktx.codegen.model.type.`class`.value

import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.parameter.Parameter

interface ValueClassConstructor : Constructor {
    val parameter: Parameter

    override val parameters: Collection<Parameter>
        get() = listOf(parameter)
}