package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.openapi.client.extension.toPoetClassName

class AnnotationSpec(
    private val annotation: Annotation
) {
    fun build(): com.squareup.kotlinpoet.AnnotationSpec {
        val annotationType = annotation.className.toPoetClassName()
        val builder = com.squareup.kotlinpoet.AnnotationSpec.builder(annotationType)
        annotation.members.forEach { member -> builder.addMember("${member.name} = ${member.value}") }
        return builder.build()
    }
}