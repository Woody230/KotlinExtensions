package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`class`.ClassModifier
import com.squareup.kotlinpoet.KModifier

fun ClassModifier.toPoetModifier(): KModifier = when (this) {
    ClassModifier.PUBLIC -> KModifier.PUBLIC
    ClassModifier.PROTECTED -> KModifier.PROTECTED
    ClassModifier.PRIVATE -> KModifier.PRIVATE
    ClassModifier.INTERNAL -> KModifier.INTERNAL
    ClassModifier.EXPECT -> KModifier.EXPECT
    ClassModifier.ACTUAL -> KModifier.ACTUAL
    ClassModifier.FINAL -> KModifier.FINAL
    ClassModifier.OPEN -> KModifier.OPEN
    ClassModifier.ABSTRACT -> KModifier.ABSTRACT
    ClassModifier.SEALED -> KModifier.SEALED
    ClassModifier.EXTERNAL -> KModifier.EXTERNAL
    ClassModifier.INNER -> KModifier.INNER
}