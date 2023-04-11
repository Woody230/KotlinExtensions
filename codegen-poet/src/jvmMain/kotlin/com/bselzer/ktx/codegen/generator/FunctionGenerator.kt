package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.function.Function
import com.squareup.kotlinpoet.FunSpec

class FunctionGenerator(
    private val function: Function
) {
    fun build(): FunSpec {
        val annotations = function.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        val parameters = function.parameters.map(::ParameterGenerator).map(ParameterGenerator::build)
        return FunSpec.builder(function.name).apply {
            function.documentation?.let { documentation -> addKdoc(documentation.toString()) }
            addAnnotations(annotations)
            addParameters(parameters)
        }.build()
    }
}