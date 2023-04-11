package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.annotation.toPoetSiteTarget
import com.bselzer.ktx.codegen.model.type.toPoetClassName
import com.squareup.kotlinpoet.AnnotationSpec

class AnnotationGenerator(
    private val annotation: Annotation
) {
    fun build(): AnnotationSpec {
        val annotationType = annotation.className.toPoetClassName()
        val siteTarget = annotation.siteTarget?.toPoetSiteTarget()

        val builder = AnnotationSpec.builder(annotationType)
        annotation.members.forEach { member -> builder.addMember("${member.name} = ${member.value}") }
        return builder.useSiteTarget(siteTarget).build()
    }
}