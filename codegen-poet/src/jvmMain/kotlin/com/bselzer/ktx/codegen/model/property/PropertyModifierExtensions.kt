package com.bselzer.ktx.codegen.model.property

import com.squareup.kotlinpoet.KModifier

fun PropertyModifier.toPoetModifier(): KModifier = when (this) {
    PropertyModifier.PUBLIC -> KModifier.PUBLIC
    PropertyModifier.PROTECTED -> KModifier.PROTECTED
    PropertyModifier.PRIVATE -> KModifier.PRIVATE
    PropertyModifier.INTERNAL -> KModifier.INTERNAL
    PropertyModifier.EXPECT -> KModifier.EXPECT
    PropertyModifier.ACTUAL -> KModifier.ACTUAL
    PropertyModifier.FINAL -> KModifier.FINAL
    PropertyModifier.OPEN -> KModifier.OPEN
    PropertyModifier.ABSTRACT -> KModifier.ABSTRACT
    PropertyModifier.CONST -> KModifier.CONST
    PropertyModifier.EXTERNAL -> KModifier.EXTERNAL
    PropertyModifier.OVERRIDE -> KModifier.OVERRIDE
    PropertyModifier.LATEINIT -> KModifier.LATEINIT
}