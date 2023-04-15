package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`object`.ObjectModifier
import com.squareup.kotlinpoet.KModifier

fun ObjectModifier.toPoetModifier(): KModifier = when (this) {
    ObjectModifier.PUBLIC -> KModifier.PUBLIC
    ObjectModifier.PROTECTED -> KModifier.PROTECTED
    ObjectModifier.PRIVATE -> KModifier.PRIVATE
    ObjectModifier.INTERNAL -> KModifier.INTERNAL
    ObjectModifier.EXPECT -> KModifier.EXPECT
    ObjectModifier.ACTUAL -> KModifier.ACTUAL
    ObjectModifier.EXTERNAL -> KModifier.EXTERNAL
}