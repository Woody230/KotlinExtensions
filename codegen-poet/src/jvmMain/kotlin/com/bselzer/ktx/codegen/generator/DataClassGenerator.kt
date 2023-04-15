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
import com.bselzer.ktx.codegen.model.extensions.addSuperClass
import com.bselzer.ktx.codegen.model.extensions.addSuperInterfaces
import com.bselzer.ktx.codegen.model.extensions.addTypeVariables
import com.bselzer.ktx.codegen.model.extensions.addValueClasses
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClassModifier
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

interface DataClassGenerator {
    fun build(dataClass: DataClass): TypeSpec

    companion object : DataClassGenerator {
        override fun build(dataClass: DataClass): TypeSpec {
            val modifiers = dataClass.modifiers.map(DataClassModifier::toPoetModifier) + KModifier.DATA
            return TypeSpec.classBuilder(dataClass.name).apply {
                addDocumentation(dataClass.documentation)
                addAnnotations(dataClass.annotations)
                addModifiers(modifiers)
                addTypeVariables(dataClass.typeVariables)
                addPrimaryConstructor(dataClass.primaryConstructor)
                addSuperClass(dataClass.superClass)
                addSuperInterfaces(dataClass.superInterfaces)
                addProperties(dataClass.properties)
                addInitializer(dataClass.initializer)
                addFunctions(dataClass.functions)
                addObjects(dataClass.objects)
                addCompanionObject(dataClass.companionObject)
                addClasses(dataClass.classes)
                addValueClasses(dataClass.valueClasses)
                addEnumClasses(dataClass.enumClasses)
                addDataClasses(dataClass.dataClasses)
                addAnnotationClasses(dataClass.annotationClasses)
                addInterfaces(dataClass.interfaces)
                addFunctionalInterfaces(dataClass.functionalInterfaces)
            }.build()
        }
    }
}