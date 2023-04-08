package com.bselzer.ktx.openapi.client.internal

import com.bselzer.ktx.openapi.client.type.name.ClassName
import com.bselzer.ktx.openapi.client.type.name.ParameterizedTypeName
import com.bselzer.ktx.openapi.client.type.name.TypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

internal fun TypeName.toPoetTypeName(): com.squareup.kotlinpoet.TypeName = when (this) {
    is ClassName -> toPoetClassName()
    is ParameterizedTypeName -> toPoetParameterizedTypeName()
    else -> throw NotImplementedError("Type name not supported: $this")
}

internal fun ClassName.toPoetClassName(): com.squareup.kotlinpoet.ClassName = com.squareup.kotlinpoet.ClassName(packageName, className)
internal fun ParameterizedTypeName.toPoetParameterizedTypeName(): com.squareup.kotlinpoet.ParameterizedTypeName {
    val parameters = parameters.map { parameter -> parameter.toPoetTypeName() }
    return root.toPoetClassName().parameterizedBy(parameters)
}