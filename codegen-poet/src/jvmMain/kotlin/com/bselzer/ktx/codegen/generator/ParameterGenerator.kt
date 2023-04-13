package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.parameter.ParameterModifier
import com.bselzer.ktx.codegen.model.parameter.toPoetModifier
import com.bselzer.ktx.codegen.model.type.toPoetTypeName
import com.squareup.kotlinpoet.ParameterSpec

interface ParameterGenerator {
    fun build(parameter: Parameter): ParameterSpec

    companion object : ParameterGenerator {
        override fun build(parameter: Parameter): ParameterSpec {
            val type = parameter.type.toPoetTypeName()
            val annotations = parameter.annotations.map(AnnotationGenerator::build)
            val modifiers = parameter.modifiers.map(ParameterModifier::toPoetModifier)
            return ParameterSpec.builder(parameter.name, type).apply {
                addAnnotations(annotations)
                addModifiers(modifiers)
                parameter.documentation?.let { documentation -> addKdoc(documentation.toString()) }
                parameter.defaultValue?.let { default -> defaultValue(default.toString()) }
            }.build()
        }
    }
}