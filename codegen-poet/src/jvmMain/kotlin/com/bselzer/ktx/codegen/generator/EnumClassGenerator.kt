package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.addAnnotationClasses
import com.bselzer.ktx.codegen.model.extensions.addAnnotations
import com.bselzer.ktx.codegen.model.extensions.addClasses
import com.bselzer.ktx.codegen.model.extensions.addCompanionObject
import com.bselzer.ktx.codegen.model.extensions.addDataClasses
import com.bselzer.ktx.codegen.model.extensions.addDocumentation
import com.bselzer.ktx.codegen.model.extensions.addEnumClasses
import com.bselzer.ktx.codegen.model.extensions.addEnumConstants
import com.bselzer.ktx.codegen.model.extensions.addEnumConstructor
import com.bselzer.ktx.codegen.model.extensions.addFunctionalInterfaces
import com.bselzer.ktx.codegen.model.extensions.addFunctions
import com.bselzer.ktx.codegen.model.extensions.addInitializer
import com.bselzer.ktx.codegen.model.extensions.addInterfaces
import com.bselzer.ktx.codegen.model.extensions.addObjects
import com.bselzer.ktx.codegen.model.extensions.addProperties
import com.bselzer.ktx.codegen.model.extensions.addSuperInterfaces
import com.bselzer.ktx.codegen.model.extensions.addValueClasses
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClassModifier
import com.squareup.kotlinpoet.TypeSpec

interface EnumClassGenerator {
    fun build(enumClass: EnumClass): TypeSpec

    companion object : EnumClassGenerator {
        override fun build(enumClass: EnumClass): TypeSpec {
            val modifiers = enumClass.modifiers.map(EnumClassModifier::toPoetModifier)
            return TypeSpec.enumBuilder(enumClass.name).apply {
                addDocumentation(enumClass.documentation)
                addAnnotations(enumClass.annotations)
                addModifiers(modifiers)
                addEnumConstants(enumClass.constants)
                addEnumConstructor(enumClass.primaryConstructor)
                addSuperInterfaces(enumClass.superInterfaces)
                addProperties(enumClass.properties)
                addInitializer(enumClass.initializer)
                addFunctions(enumClass.functions)
                addObjects(enumClass.objects)
                addCompanionObject(enumClass.companionObject)
                addClasses(enumClass.classes)
                addValueClasses(enumClass.valueClasses)
                addEnumClasses(enumClass.enumClasses)
                addDataClasses(enumClass.dataClasses)
                addAnnotationClasses(enumClass.annotationClasses)
                addInterfaces(enumClass.interfaces)
                addFunctionalInterfaces(enumClass.functionalInterfaces)
            }.build()
        }
    }
}