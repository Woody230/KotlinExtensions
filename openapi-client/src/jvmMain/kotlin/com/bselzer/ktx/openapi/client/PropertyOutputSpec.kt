package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.client.internal.toPoetClassName
import com.bselzer.ktx.openapi.client.internal.toPoetTypeName
import com.bselzer.ktx.openapi.client.property.PropertyOutput
import com.bselzer.ktx.openapi.client.type.name.ClassName
import com.bselzer.ktx.openapi.client.type.name.Serializable
import com.squareup.kotlinpoet.AnnotationSpec
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
}