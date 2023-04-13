package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.name.Variance
import com.squareup.kotlinpoet.KModifier

fun Variance.toPoetModifier(): KModifier = when (this) {
    Variance.IN -> KModifier.IN
    Variance.OUT -> KModifier.OUT
}