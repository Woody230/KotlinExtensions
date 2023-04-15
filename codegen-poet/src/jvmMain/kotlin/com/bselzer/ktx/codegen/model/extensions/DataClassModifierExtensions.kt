package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`class`.data.DataClassModifier
import com.squareup.kotlinpoet.KModifier

fun DataClassModifier.toPoetModifier(): KModifier = when (this) {
    DataClassModifier.PUBLIC -> KModifier.PUBLIC
    DataClassModifier.PROTECTED -> KModifier.PROTECTED
    DataClassModifier.PRIVATE -> KModifier.PRIVATE
    DataClassModifier.INTERNAL -> KModifier.INTERNAL
    DataClassModifier.EXTERNAL -> KModifier.EXTERNAL
}