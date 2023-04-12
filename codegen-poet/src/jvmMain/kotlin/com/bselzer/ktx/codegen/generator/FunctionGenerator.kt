package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.function.FunctionModifier
import com.bselzer.ktx.codegen.model.function.toPoetModifier
import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.codegen.model.type.TypeVariableName
import com.bselzer.ktx.codegen.model.type.toPoetTypeName
import com.bselzer.ktx.codegen.model.type.toPoetTypeVariableName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec

class FunctionGenerator(
    private val function: Function
) {
    @OptIn(ExperimentalKotlinPoetApi::class)
    fun build(): FunSpec {
        val annotations = function.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        val parameters = function.parameters.map(::ParameterGenerator).map(ParameterGenerator::build)
        val modifiers = function.modifiers.map(FunctionModifier::toPoetModifier)
        val typeVariables = function.typeVariables.map(TypeVariableName::toPoetTypeVariableName)
        val contextReceivers = function.contextReceivers.map(TypeName::toPoetTypeName)
        return FunSpec.builder(function.name).apply {
            addAnnotations(annotations)
            addParameters(parameters)
            addModifiers(modifiers)
            addTypeVariables(typeVariables)
            contextReceivers(contextReceivers)
            addCode(function.body.toString())

            function.documentation?.let { documentation -> addKdoc(documentation.toString()) }
            function.receiver?.let { receiver -> receiver(receiver.toPoetTypeName()) }
            function.returns?.let { returns -> returns(returns.toPoetTypeName()) }
        }.build()
    }
}