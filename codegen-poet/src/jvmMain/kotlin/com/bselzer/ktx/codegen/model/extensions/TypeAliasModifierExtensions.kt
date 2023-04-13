package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.alias.TypeAliasModifier
import com.squareup.kotlinpoet.KModifier

fun TypeAliasModifier.toPoetModifier(): KModifier = when (this) {
    TypeAliasModifier.PUBLIC -> KModifier.PUBLIC
    TypeAliasModifier.INTERNAL -> KModifier.INTERNAL
    TypeAliasModifier.PRIVATE -> KModifier.PRIVATE
    TypeAliasModifier.ACTUAL -> KModifier.ACTUAL
}