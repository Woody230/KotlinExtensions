package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.toPoetTypeName
import com.squareup.kotlinpoet.ParameterSpec

class ParameterGenerator(
    private val parameter: Parameter
) {
    fun build(): ParameterSpec {
        val type = parameter.typeName.toPoetTypeName()
        val annotations = parameter.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        return ParameterSpec.builder(parameter.name, type).apply {
            addAnnotations(annotations)
            parameter.documentation?.let { documentation -> addKdoc(documentation.toString()) }
        }.build()
    }
}