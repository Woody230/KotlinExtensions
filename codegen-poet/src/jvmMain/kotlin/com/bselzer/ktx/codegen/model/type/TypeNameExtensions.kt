package com.bselzer.ktx.codegen.model.type

import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun TypeName.toPoetTypeName(): com.squareup.kotlinpoet.TypeName = when (this) {
    is ClassName -> toPoetClassName()
    is ParameterizedTypeName -> toPoetParameterizedTypeName()
    is TypeVariableName -> toPoetTypeVariableName()
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