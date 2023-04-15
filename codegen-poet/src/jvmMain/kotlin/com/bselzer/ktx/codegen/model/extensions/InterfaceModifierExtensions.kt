package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`interface`.InterfaceModifier
import com.squareup.kotlinpoet.KModifier

fun InterfaceModifier.toPoetModifier(): KModifier = when (this) {
    InterfaceModifier.PUBLIC -> KModifier.PUBLIC
    InterfaceModifier.PROTECTED -> KModifier.PROTECTED
    InterfaceModifier.PRIVATE -> KModifier.PRIVATE
    InterfaceModifier.INTERNAL -> KModifier.INTERNAL
    InterfaceModifier.EXPECT -> KModifier.EXPECT
    InterfaceModifier.ACTUAL -> KModifier.ACTUAL
    InterfaceModifier.SEALED -> KModifier.SEALED
    InterfaceModifier.EXTERNAL -> KModifier.EXTERNAL
}