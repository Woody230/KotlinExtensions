package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.addAnnotationClasses
import com.bselzer.ktx.codegen.model.extensions.addAnnotations
import com.bselzer.ktx.codegen.model.extensions.addClasses
import com.bselzer.ktx.codegen.model.extensions.addCompanionObject
import com.bselzer.ktx.codegen.model.extensions.addDataClasses
import com.bselzer.ktx.codegen.model.extensions.addDocumentation
import com.bselzer.ktx.codegen.model.extensions.addEnumClasses
import com.bselzer.ktx.codegen.model.extensions.addFunctionalInterfaces
import com.bselzer.ktx.codegen.model.extensions.addFunctions
import com.bselzer.ktx.codegen.model.extensions.addInterfaces
import com.bselzer.ktx.codegen.model.extensions.addObjects
import com.bselzer.ktx.codegen.model.extensions.addProperties
import com.bselzer.ktx.codegen.model.extensions.addSuperInterfaces
import com.bselzer.ktx.codegen.model.extensions.addTypeVariables
import com.bselzer.ktx.codegen.model.extensions.addValueClasses
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.InterfaceModifier
import com.squareup.kotlinpoet.TypeSpec

interface InterfaceGenerator {
    fun build(`interface`: Interface): TypeSpec

    companion object : InterfaceGenerator {
        override fun build(`interface`: Interface): TypeSpec {
            val modifiers = `interface`.modifiers.map(InterfaceModifier::toPoetModifier)
            return TypeSpec.interfaceBuilder(`interface`.name).apply {
                addDocumentation(`interface`.documentation)
                addAnnotations(`interface`.annotations)
                addModifiers(modifiers)
                addTypeVariables(`interface`.typeVariables)
                addProperties(`interface`.properties)
                addSuperInterfaces(`interface`.superInterfaces)
                addFunctions(`interface`.functions)
                addObjects(`interface`.objects)
                addCompanionObject(`interface`.companionObject)
                addClasses(`interface`.classes)
                addValueClasses(`interface`.valueClasses)
                addEnumClasses(`interface`.enumClasses)
                addDataClasses(`interface`.dataClasses)
                addAnnotationClasses(`interface`.annotationClasses)
                addInterfaces(`interface`.interfaces)
                addFunctionalInterfaces(`interface`.functionalInterfaces)
            }.build()
        }
    }
}