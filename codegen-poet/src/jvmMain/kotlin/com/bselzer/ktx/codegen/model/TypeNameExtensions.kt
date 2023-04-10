package com.bselzer.ktx.codegen.model

import com.bselzer.ktx.codegen.model.type.ClassName
import com.bselzer.ktx.codegen.model.type.ParameterizedTypeName
import com.bselzer.ktx.codegen.model.type.TypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun TypeName.toPoetTypeName(): com.squareup.kotlinpoet.TypeName = when (this) {
    is ClassName -> toPoetClassName()
    is ParameterizedTypeName -> toPoetParameterizedTypeName()
    else -> throw NotImplementedError("Type name not supported: $this")
}

fun ClassName.toPoetClassName(): com.squareup.kotlinpoet.ClassName = com.squareup.kotlinpoet.ClassName(packageName, className)
fun ParameterizedTypeName.toPoetParameterizedTypeName(): com.squareup.kotlinpoet.ParameterizedTypeName {
    val parameters = parameters.map { parameter -> parameter.toPoetTypeName() }
    return root.toPoetClassName().parameterizedBy(parameters)
}