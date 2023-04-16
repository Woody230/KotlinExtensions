package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.extensions.toPoetTypeName
import com.bselzer.ktx.codegen.model.extensions.toPoetTypeVariableName
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.function.FunctionModifier
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec

interface FunctionGenerator {
    fun build(function: Function): FunSpec

    companion object : FunctionGenerator {
        @OptIn(ExperimentalKotlinPoetApi::class)
        override fun build(function: Function): FunSpec {
            val annotations = function.annotations.map(AnnotationGenerator::build)
            val parameters = function.parameters.map(ParameterGenerator::build)
            val modifiers = function.modifiers.map(FunctionModifier::toPoetModifier)
            val typeVariables = function.typeVariables.map(TypeVariableName::toPoetTypeVariableName)
            val contextReceivers = function.contextReceivers.map(TypeName::toPoetTypeName)
            return FunSpec.builder(function.name).apply {
                addAnnotations(annotations)
                addParameters(parameters)
                addModifiers(modifiers)
                addTypeVariables(typeVariables)
                contextReceivers(contextReceivers)
                function.body?.let { body -> addCode(body.toString()) }

                function.documentation?.let { documentation -> addKdoc(documentation.toString()) }
                function.receiver?.let { receiver -> receiver(receiver.toPoetTypeName()) }
                function.returns?.let { returns -> returns(returns.toPoetTypeName()) }
            }.build()
        }
    }
}