package com.bselzer.ktx.codegen.model.type

import com.squareup.kotlinpoet.KModifier

fun Variance.toPoetModifier(): KModifier = when (this) {
    Variance.IN -> KModifier.IN
    Variance.OUT -> KModifier.OUT
}