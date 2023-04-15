package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClassModifier
import com.squareup.kotlinpoet.KModifier

fun EnumClassModifier.toPoetModifier(): KModifier = when (this) {
    EnumClassModifier.PUBLIC -> KModifier.PUBLIC
    EnumClassModifier.PROTECTED -> KModifier.PROTECTED
    EnumClassModifier.PRIVATE -> KModifier.PRIVATE
    EnumClassModifier.INTERNAL -> KModifier.INTERNAL
    EnumClassModifier.EXPECT -> KModifier.EXPECT
    EnumClassModifier.ACTUAL -> KModifier.ACTUAL
    EnumClassModifier.EXTERNAL -> KModifier.EXTERNAL
}