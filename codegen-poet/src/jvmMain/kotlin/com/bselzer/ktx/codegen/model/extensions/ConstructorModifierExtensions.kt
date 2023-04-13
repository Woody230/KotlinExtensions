package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.squareup.kotlinpoet.KModifier

fun ConstructorModifier.toPoetModifier(): KModifier = when (this) {
    ConstructorModifier.PUBLIC -> KModifier.PUBLIC
    ConstructorModifier.PROTECTED -> KModifier.PROTECTED
    ConstructorModifier.PRIVATE -> KModifier.PRIVATE
    ConstructorModifier.INTERNAL -> KModifier.INTERNAL
}