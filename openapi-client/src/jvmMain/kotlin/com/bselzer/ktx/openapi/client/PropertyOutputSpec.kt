package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.client.property.PropertyOutput
import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.client.type.ParameterizedTypeName
import com.bselzer.ktx.openapi.client.type.Serializable
import com.bselzer.ktx.openapi.client.type.TypeName
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec

class PropertyOutputSpec(
    private val output: PropertyOutput,
    private val name: String
) {
    fun build(): PropertySpec {
        val type = output.typeName.toPoetTypeName().copy(nullable = output.nullable)
        return PropertySpec.builder(name, type).apply {
            addDescription()
            addSerializableAnnotation()
            initializer(output.instantiation)
        }.build()
    }

    private fun PropertySpec.Builder.addDescription() {
        output.description?.let { description ->
            // TODO character validation needed?
            addKdoc(description)
        }
    }

    private fun PropertySpec.Builder.addSerializableAnnotation() {
        output.serializable?.let { serializable ->
            val annotation = serializable.annotation()
            addAnnotation(annotation)
        }
    }

    private fun Serializable.annotation(): AnnotationSpec {
        val annotationType = ClassName.SERIALIZABLE.toPoetClassName()
        val serializerType = className.toPoetClassName()
        return AnnotationSpec.builder(annotationType).addMember("with = $serializerType::class").build()
    }

    private fun TypeName.toPoetTypeName(): com.squareup.kotlinpoet.TypeName = when (this) {
        is ClassName -> toPoetClassName()
        is ParameterizedTypeName -> toPoetParameterizedTypeName()
        else -> throw NotImplementedError("Type name not supported: $this")
    }

    private fun ClassName.toPoetClassName(): com.squareup.kotlinpoet.ClassName = com.squareup.kotlinpoet.ClassName(packageName, className)
    private fun ParameterizedTypeName.toPoetParameterizedTypeName(): com.squareup.kotlinpoet.ParameterizedTypeName {
        val parameters = parameters.map { parameter -> parameter.toPoetTypeName() }
        return root.toPoetClassName().parameterizedBy(parameters)
    }
}