package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.ConstructorReference
import com.bselzer.ktx.codegen.model.constructor.ConstructorReferenceType
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.extensions.toPoetTypeVariableName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.squareup.kotlinpoet.FunSpec

interface ConstructorGenerator {
    fun build(function: Constructor): FunSpec

    companion object : ConstructorGenerator {
        override fun build(function: Constructor): FunSpec {
            fun FunSpec.Builder.callReference(reference: ConstructorReference) {
                val arguments = reference.arguments.map(CodeBlock::toString).toTypedArray()
                when (reference.type) {
                    ConstructorReferenceType.THIS -> callThisConstructor(*arguments)
                    ConstructorReferenceType.SUPER -> callSuperConstructor(*arguments)
                }
            }

            val annotations = function.annotations.map(AnnotationGenerator::build)
            val parameters = function.parameters.map(ParameterGenerator::build)
            val modifiers = function.modifiers.map(ConstructorModifier::toPoetModifier)
            val typeVariables = function.typeVariables.map(TypeVariableName::toPoetTypeVariableName)
            return FunSpec.constructorBuilder().apply {
                addAnnotations(annotations)
                addParameters(parameters)
                addModifiers(modifiers)
                addTypeVariables(typeVariables)
                addCode(function.body.toString())

                function.documentation?.let { documentation -> addKdoc(documentation.toString()) }
                function.reference?.let(::callReference)
            }.build()
        }
    }
}