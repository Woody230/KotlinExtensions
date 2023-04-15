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
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterfaceModifier
import com.squareup.kotlinpoet.TypeSpec

interface FunctionalInterfaceGenerator {
    fun build(functionalInterface: FunctionalInterface): TypeSpec

    companion object : FunctionalInterfaceGenerator {
        override fun build(functionalInterface: FunctionalInterface): TypeSpec {
            val modifiers = functionalInterface.modifiers.map(FunctionalInterfaceModifier::toPoetModifier)
            return TypeSpec.funInterfaceBuilder(functionalInterface.name).apply {
                addDocumentation(functionalInterface.documentation)
                addAnnotations(functionalInterface.annotations)
                addModifiers(modifiers)
                addTypeVariables(functionalInterface.typeVariables)
                addProperties(functionalInterface.properties)
                addSuperInterfaces(functionalInterface.superInterfaces)
                addFunctions(functionalInterface.functions)
                addObjects(functionalInterface.objects)
                addCompanionObject(functionalInterface.companionObject)
                addClasses(functionalInterface.classes)
                addValueClasses(functionalInterface.valueClasses)
                addEnumClasses(functionalInterface.enumClasses)
                addDataClasses(functionalInterface.dataClasses)
                addAnnotationClasses(functionalInterface.annotationClasses)
                addInterfaces(functionalInterface.interfaces)
                addFunctionalInterfaces(functionalInterface.functionalInterfaces)
            }.build()
        }
    }
}