package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.parameter.ParameterModifier
import com.squareup.kotlinpoet.KModifier

fun ParameterModifier.toPoetModifier(): KModifier = when (this) {
    ParameterModifier.PUBLIC -> KModifier.PUBLIC
    ParameterModifier.PROTECTED -> KModifier.PROTECTED
    ParameterModifier.PRIVATE -> KModifier.PRIVATE
    ParameterModifier.INTERNAL -> KModifier.INTERNAL
    ParameterModifier.VARARG -> KModifier.VARARG
    ParameterModifier.NOINLINE -> KModifier.NOINLINE
    ParameterModifier.CROSSINLINE -> KModifier.CROSSINLINE
    ParameterModifier.EXTERNAL -> KModifier.EXTERNAL
    ParameterModifier.OVERRIDE -> KModifier.OVERRIDE
}