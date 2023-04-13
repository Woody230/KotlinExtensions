package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.parameter.ParameterModifier
import com.squareup.kotlinpoet.KModifier

fun ParameterModifier.toPoetModifier(): KModifier = when (this) {
    ParameterModifier.VARARG -> KModifier.VARARG
    ParameterModifier.NOINLINE -> KModifier.NOINLINE
    ParameterModifier.CROSSINLINE -> KModifier.CROSSINLINE
}