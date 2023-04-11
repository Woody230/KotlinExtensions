package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.codegen.model.type.toPoetTypeName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.PropertySpec

class PropertyGenerator(
    private val property: Property,
) {
    @OptIn(ExperimentalKotlinPoetApi::class)
    fun build(): PropertySpec {
        val type = property.typeName.toPoetTypeName().copy(nullable = property.nullable)
        val annotations = property.annotations.map(::AnnotationGenerator).map(AnnotationGenerator::build)
        val contextReceivers = property.contextReceivers.map(TypeName::toPoetTypeName)
        return PropertySpec.builder(property.name, type).apply {
            mutable(property.mutable)
            addAnnotations(annotations)
            receiver(property.receiver?.toPoetTypeName())
            contextReceivers(contextReceivers)

            property.description?.let { description -> addKdoc(description.toString()) }
            property.delegated?.let { delegated -> delegate(delegated.toString()) }
            property.initializer?.let { init -> initializer(init.toString()) }
            property.getter?.let { get -> getter(FunctionGenerator(get).build()) }
            property.setter?.let { set -> setter(FunctionGenerator(set).build()) }
        }.build()
    }
}