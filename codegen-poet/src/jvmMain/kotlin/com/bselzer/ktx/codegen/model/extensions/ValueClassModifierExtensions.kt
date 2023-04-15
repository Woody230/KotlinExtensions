package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClassModifier
import com.squareup.kotlinpoet.KModifier

fun ValueClassModifier.toPoetModifier(): KModifier = when (this) {
    ValueClassModifier.PUBLIC -> KModifier.PUBLIC
    ValueClassModifier.PROTECTED -> KModifier.PROTECTED
    ValueClassModifier.PRIVATE -> KModifier.PRIVATE
    ValueClassModifier.INTERNAL -> KModifier.INTERNAL
    ValueClassModifier.EXPECT -> KModifier.EXPECT
    ValueClassModifier.ACTUAL -> KModifier.ACTUAL
    ValueClassModifier.EXTERNAL -> KModifier.EXTERNAL
}