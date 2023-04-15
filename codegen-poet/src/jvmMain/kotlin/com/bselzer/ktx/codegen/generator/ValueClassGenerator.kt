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
import com.bselzer.ktx.codegen.model.extensions.addInitializer
import com.bselzer.ktx.codegen.model.extensions.addInterfaces
import com.bselzer.ktx.codegen.model.extensions.addObjects
import com.bselzer.ktx.codegen.model.extensions.addPrimaryConstructor
import com.bselzer.ktx.codegen.model.extensions.addProperties
import com.bselzer.ktx.codegen.model.extensions.addSuperInterfaces
import com.bselzer.ktx.codegen.model.extensions.addTypeVariables
import com.bselzer.ktx.codegen.model.extensions.addValueClasses
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClassModifier
import com.squareup.kotlinpoet.TypeSpec

interface ValueClassGenerator {
    fun build(valueClass: ValueClass): TypeSpec

    companion object : ValueClassGenerator {
        override fun build(valueClass: ValueClass): TypeSpec {
            val modifiers = valueClass.modifiers.map(ValueClassModifier::toPoetModifier)
            return TypeSpec.valueClassBuilder(valueClass.name).apply {
                addDocumentation(valueClass.documentation)
                addAnnotations(valueClass.annotations)
                addModifiers(modifiers)
                addTypeVariables(valueClass.typeVariables)
                addPrimaryConstructor(valueClass.primaryConstructor)
                addSuperInterfaces(valueClass.superInterfaces)
                addProperties(valueClass.properties)
                addInitializer(valueClass.initializer)
                addFunctions(valueClass.functions)
                addObjects(valueClass.objects)
                addCompanionObject(valueClass.companionObject)
                addClasses(valueClass.classes)
                addValueClasses(valueClass.valueClasses)
                addEnumClasses(valueClass.enumClasses)
                addDataClasses(valueClass.dataClasses)
                addAnnotationClasses(valueClass.annotationClasses)
                addInterfaces(valueClass.interfaces)
                addFunctionalInterfaces(valueClass.functionalInterfaces)
            }.build()
        }
    }
}