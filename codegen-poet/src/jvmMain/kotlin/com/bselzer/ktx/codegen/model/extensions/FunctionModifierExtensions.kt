package com.bselzer.ktx.codegen.model.function

import com.squareup.kotlinpoet.KModifier

fun FunctionModifier.toPoetModifier(): KModifier = when (this) {
    FunctionModifier.PUBLIC -> KModifier.PUBLIC
    FunctionModifier.PROTECTED -> KModifier.PROTECTED
    FunctionModifier.PRIVATE -> KModifier.PRIVATE
    FunctionModifier.INTERNAL -> KModifier.INTERNAL
    FunctionModifier.EXPECT -> KModifier.EXPECT
    FunctionModifier.ACTUAL -> KModifier.ACTUAL
    FunctionModifier.FINAL -> KModifier.FINAL
    FunctionModifier.OPEN -> KModifier.OPEN
    FunctionModifier.ABSTRACT -> KModifier.ABSTRACT
    FunctionModifier.EXTERNAL -> KModifier.EXTERNAL
    FunctionModifier.OVERRIDE -> KModifier.OVERRIDE
    FunctionModifier.TAILREC -> KModifier.TAILREC
    FunctionModifier.SUSPEND -> KModifier.SUSPEND
    FunctionModifier.INLINE -> KModifier.INLINE
    FunctionModifier.INFIX -> KModifier.INFIX
    FunctionModifier.OPERATOR -> KModifier.OPERATOR
}