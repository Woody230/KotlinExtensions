package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClassModifier
import com.squareup.kotlinpoet.KModifier

fun AnnotationClassModifier.toPoetModifier(): KModifier = when (this) {
    AnnotationClassModifier.PUBLIC -> KModifier.PUBLIC
    AnnotationClassModifier.PROTECTED -> KModifier.PROTECTED
    AnnotationClassModifier.PRIVATE -> KModifier.PRIVATE
    AnnotationClassModifier.INTERNAL -> KModifier.INTERNAL
    AnnotationClassModifier.EXPECT -> KModifier.EXPECT
    AnnotationClassModifier.ACTUAL -> KModifier.ACTUAL
    AnnotationClassModifier.EXTERNAL -> KModifier.EXTERNAL
}