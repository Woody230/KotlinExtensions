package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.function.FunctionModifier
import com.bselzer.ktx.codegen.model.function.toPoetModifier
import com.squareup.kotlinpoet.FunSpec

class FunctionGenerator(
    private val function: Function
) {
    fun build(): FunSpec {
        val annotations = function.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        val parameters = function.parameters.map(::ParameterGenerator).map(ParameterGenerator::build)
        val modifiers = function.modifiers.map(FunctionModifier::toPoetModifier)
        return FunSpec.builder(function.name).apply {
            addAnnotations(annotations)
            addParameters(parameters)
            addModifiers(modifiers)

            function.documentation?.let { documentation -> addKdoc(documentation.toString()) }
        }.build()
    }
}