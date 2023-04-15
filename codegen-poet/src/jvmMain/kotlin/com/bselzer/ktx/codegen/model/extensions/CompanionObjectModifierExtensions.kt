package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObjectModifier
import com.squareup.kotlinpoet.KModifier

fun CompanionObjectModifier.toPoetModifier(): KModifier = when (this) {
    CompanionObjectModifier.PUBLIC -> KModifier.PUBLIC
    CompanionObjectModifier.PROTECTED -> KModifier.PROTECTED
    CompanionObjectModifier.PRIVATE -> KModifier.PRIVATE
    CompanionObjectModifier.INTERNAL -> KModifier.INTERNAL
    CompanionObjectModifier.EXTERNAL -> KModifier.EXTERNAL
}