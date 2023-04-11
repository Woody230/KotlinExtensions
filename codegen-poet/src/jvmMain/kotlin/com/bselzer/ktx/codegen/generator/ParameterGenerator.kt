package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.parameter.ParameterModifier
import com.bselzer.ktx.codegen.model.parameter.toPoetModifier
import com.bselzer.ktx.codegen.model.toPoetTypeName
import com.squareup.kotlinpoet.ParameterSpec

class ParameterGenerator(
    private val parameter: Parameter
) {
    fun build(): ParameterSpec {
        val type = parameter.typeName.toPoetTypeName()
        val annotations = parameter.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        val modifiers = parameter.modifiers.map(ParameterModifier::toPoetModifier)
        return ParameterSpec.builder(parameter.name, type).apply {
            addAnnotations(annotations)
            addModifiers(modifiers)
            parameter.documentation?.let { documentation -> addKdoc(documentation.toString()) }
        }.build()
    }
}