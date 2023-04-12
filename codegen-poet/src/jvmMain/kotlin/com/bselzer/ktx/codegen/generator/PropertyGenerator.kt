package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.PropertyModifier
import com.bselzer.ktx.codegen.model.property.toPoetModifier
import com.bselzer.ktx.codegen.model.type.TypeName
import com.bselzer.ktx.codegen.model.type.TypeVariableName
import com.bselzer.ktx.codegen.model.type.toPoetTypeName
import com.bselzer.ktx.codegen.model.type.toPoetTypeVariableName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.PropertySpec

interface PropertyGenerator {
    fun build(property: Property): PropertySpec

    companion object : PropertyGenerator {
        @OptIn(ExperimentalKotlinPoetApi::class)
        override fun build(property: Property): PropertySpec {
            val type = property.typeName.toPoetTypeName()
            val annotations = property.annotations.map(AnnotationGenerator::build)
            val contextReceivers = property.contextReceivers.map(TypeName::toPoetTypeName)
            val typeVariables = property.typeVariables.map(TypeVariableName::toPoetTypeVariableName)
            val modifiers = property.modifiers.map(PropertyModifier::toPoetModifier)
            return PropertySpec.builder(property.name, type).apply {
                mutable(property.mutable)
                addAnnotations(annotations)
                contextReceivers(contextReceivers)
                addTypeVariables(typeVariables)
                addModifiers(modifiers)

                property.receiver?.let { receiver -> receiver(receiver.toPoetTypeName()) }
                property.description?.let { description -> addKdoc(description.toString()) }
                property.delegated?.let { delegated -> delegate(delegated.toString()) }
                property.initializer?.let { init -> initializer(init.toString()) }
                property.getter?.let { get -> getter(FunctionGenerator.build(get)) }
                property.setter?.let { set -> setter(FunctionGenerator.build(set)) }
            }.build()
        }
    }
}