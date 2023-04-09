package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.client.extension.toPoetTypeName
import com.bselzer.ktx.openapi.client.model.property.PropertyOutput
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
            val annotation = AnnotationSpec(serializable).build()
            addAnnotation(annotation)
        }
    }
}