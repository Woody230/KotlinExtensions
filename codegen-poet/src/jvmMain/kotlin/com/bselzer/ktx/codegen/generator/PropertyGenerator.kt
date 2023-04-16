package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.extensions.toPoetTypeName
import com.bselzer.ktx.codegen.model.extensions.toPoetTypeVariableName
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.PropertyModifier
import com.bselzer.ktx.codegen.model.property.declaration.DelegatedPropertyDeclaration
import com.bselzer.ktx.codegen.model.property.declaration.GettablePropertyDeclaration
import com.bselzer.ktx.codegen.model.property.declaration.InitializedPropertyDeclaration
import com.bselzer.ktx.codegen.model.property.declaration.PropertyDeclaration
import com.bselzer.ktx.codegen.model.property.declaration.SettablePropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.PropertySpec

interface PropertyGenerator {
    fun build(property: Property): PropertySpec

    companion object : PropertyGenerator {
        @OptIn(ExperimentalKotlinPoetApi::class)
        override fun build(property: Property): PropertySpec {
            val type = property.type.toPoetTypeName()
            val annotations = property.annotations.map(AnnotationGenerator::build)
            val contextReceivers = property.contextReceivers.map(TypeName::toPoetTypeName)
            val typeVariables = property.typeVariables.map(TypeVariableName::toPoetTypeVariableName)
            val modifiers = property.modifiers.map(PropertyModifier::toPoetModifier)
            return PropertySpec.builder(property.name, type).apply {
                addAnnotations(annotations)
                contextReceivers(contextReceivers)
                addTypeVariables(typeVariables)
                addModifiers(modifiers)

                property.receiver?.let { receiver -> receiver(receiver.toPoetTypeName()) }
                property.documentation?.let { description -> addKdoc(description.toString()) }
                addDeclaration(property.declaration)
            }.build()
        }

        private fun PropertySpec.Builder.addDeclaration(declaration: PropertyDeclaration?) {
            if (declaration == null) {
                return
            }

            mutable(declaration.mutable)

            when (declaration) {
                is DelegatedPropertyDeclaration -> {
                    delegate(declaration.delegate.toString())
                }

                is GettablePropertyDeclaration -> {
                    declaration.initializer?.let { initializer(it.toString()) }
                    getter(FunctionGenerator.build(declaration.getter))
                }

                is InitializedPropertyDeclaration -> {
                    initializer(declaration.initializer.toString())
                }

                is SettablePropertyDeclaration -> {
                    declaration.initializer?.let { initializer(it.toString()) }
                    getter(FunctionGenerator.build(declaration.getter))
                    setter(FunctionGenerator.build(declaration.setter))
                }

                else -> throw NotImplementedError("Unknown property declaration: $declaration")
            }
        }
    }
}