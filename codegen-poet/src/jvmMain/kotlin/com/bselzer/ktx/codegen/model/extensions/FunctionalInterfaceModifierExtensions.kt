package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterfaceModifier
import com.squareup.kotlinpoet.KModifier

fun FunctionalInterfaceModifier.toPoetModifier(): KModifier = when (this) {
    FunctionalInterfaceModifier.PUBLIC -> KModifier.PUBLIC
    FunctionalInterfaceModifier.PROTECTED -> KModifier.PROTECTED
    FunctionalInterfaceModifier.PRIVATE -> KModifier.PRIVATE
    FunctionalInterfaceModifier.INTERNAL -> KModifier.INTERNAL
    FunctionalInterfaceModifier.EXPECT -> KModifier.EXPECT
    FunctionalInterfaceModifier.ACTUAL -> KModifier.ACTUAL
    FunctionalInterfaceModifier.EXTERNAL -> KModifier.EXTERNAL
}