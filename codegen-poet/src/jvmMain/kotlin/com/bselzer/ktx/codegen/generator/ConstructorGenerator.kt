package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReferenceType
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.squareup.kotlinpoet.FunSpec

interface ConstructorGenerator {
    fun build(constructor: Constructor): FunSpec

    companion object : ConstructorGenerator {
        override fun build(constructor: Constructor): FunSpec {
            fun FunSpec.Builder.callReference(reference: ConstructorReference) {
                val arguments = reference.arguments.map(CodeBlock::toString).toTypedArray()
                when (reference.type) {
                    ConstructorReferenceType.THIS -> callThisConstructor(*arguments)
                    ConstructorReferenceType.SUPER -> callSuperConstructor(*arguments)
                }
            }

            val parameters = constructor.parameters.map(ParameterGenerator::build)
            val modifiers = constructor.modifiers.map(ConstructorModifier::toPoetModifier)
            return FunSpec.constructorBuilder().apply {
                addParameters(parameters)
                addModifiers(modifiers)
                constructor.body?.let { body -> addCode(body.toString()) }

                constructor.reference?.let(::callReference)
            }.build()
        }
    }
}