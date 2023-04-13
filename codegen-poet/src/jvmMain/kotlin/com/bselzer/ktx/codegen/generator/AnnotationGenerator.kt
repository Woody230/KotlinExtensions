package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.extensions.toPoetClassName
import com.bselzer.ktx.codegen.model.extensions.toPoetSiteTarget
import com.squareup.kotlinpoet.AnnotationSpec

interface AnnotationGenerator {
    fun build(annotation: Annotation): AnnotationSpec

    companion object : AnnotationGenerator {
        override fun build(annotation: Annotation): AnnotationSpec {
            val annotationType = annotation.className.toPoetClassName()
            val siteTarget = annotation.siteTarget?.toPoetSiteTarget()

            val builder = AnnotationSpec.builder(annotationType)
            annotation.members.forEach { member -> builder.addMember("${member.name} = ${member.value}") }
            return builder.useSiteTarget(siteTarget).build()
        }
    }
}