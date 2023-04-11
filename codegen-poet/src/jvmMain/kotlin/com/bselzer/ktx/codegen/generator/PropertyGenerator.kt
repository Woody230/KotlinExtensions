package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.toPoetTypeName
import com.squareup.kotlinpoet.PropertySpec

class PropertyGenerator(
    private val property: Property,
) {
    fun build(): PropertySpec {
        val type = property.typeName.toPoetTypeName().copy(nullable = property.nullable)
        val annotations = property.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        return PropertySpec.builder(property.name, type).apply {
            mutable(property.mutable)
            addAnnotations(annotations)

            property.description?.let { description -> addKdoc(description.toString()) }
            property.delegated?.let { delegated -> delegate(delegated.toString()) }
            property.initializer?.let { init -> initializer(init.toString()) }
            property.getter?.let { get -> getter(FunctionGenerator(get).build()) }
            property.setter?.let { set -> setter(FunctionGenerator(set).build()) }
        }.build()
    }
}