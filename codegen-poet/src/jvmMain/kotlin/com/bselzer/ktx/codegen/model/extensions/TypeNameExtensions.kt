package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.generator.ParameterGenerator
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.name.LambdaTypeName
import com.bselzer.ktx.codegen.model.type.name.ParameterizedTypeName
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.name.Variance
import com.bselzer.ktx.codegen.model.type.name.WildcardTypeName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun TypeName.toPoetTypeName(): com.squareup.kotlinpoet.TypeName = when (this) {
    is ClassName -> toPoetClassName()
    is ParameterizedTypeName -> toPoetParameterizedTypeName()
    is TypeVariableName -> toPoetTypeVariableName()
    is LambdaTypeName -> toPoetLambdaTypeName()
    is WildcardTypeName -> toPoetWildcardTypeName()
    else -> throw NotImplementedError("Type name not supported: $this")
}

fun ClassName.toPoetClassName(): com.squareup.kotlinpoet.ClassName {
    val className = com.squareup.kotlinpoet.ClassName(packageName, className)
    return className.copy(nullable = nullable, annotations = emptyList(), tags = emptyMap())
}

fun ParameterizedTypeName.toPoetParameterizedTypeName(): com.squareup.kotlinpoet.ParameterizedTypeName {
    val parameters = parameters.map { parameter -> parameter.toPoetTypeName() }
    return root.toPoetClassName().parameterizedBy(parameters)
}

fun TypeVariableName.toPoetTypeVariableName(): com.squareup.kotlinpoet.TypeVariableName {
    val variance = variance?.toPoetModifier()
    val bounds = bounds.map(TypeName::toPoetTypeName)
    return com.squareup.kotlinpoet.TypeVariableName.invoke(name, bounds, variance).copy(reified = reified)
}

@OptIn(ExperimentalKotlinPoetApi::class)
fun LambdaTypeName.toPoetLambdaTypeName(): com.squareup.kotlinpoet.LambdaTypeName {
    val receiver = receiver?.toPoetTypeName()
    val parameters = parameters.map(ParameterGenerator::build)
    val returns = returns?.toPoetTypeName() ?: com.squareup.kotlinpoet.UNIT
    val contextReceivers = contextReceivers.map(TypeName::toPoetTypeName)
    return com.squareup.kotlinpoet.LambdaTypeName.get(receiver, parameters, returns, contextReceivers)
}

fun WildcardTypeName.toPoetWildcardTypeName(): com.squareup.kotlinpoet.WildcardTypeName = when (variance) {
    Variance.IN -> com.squareup.kotlinpoet.WildcardTypeName.consumerOf(type.toPoetTypeName())
    Variance.OUT -> com.squareup.kotlinpoet.WildcardTypeName.producerOf(type.toPoetTypeName())
}