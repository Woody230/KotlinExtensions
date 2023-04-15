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
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.ClassModifier
import com.squareup.kotlinpoet.TypeSpec

interface ClassGenerator {
    fun build(`class`: Class): TypeSpec

    companion object : ClassGenerator {
        override fun build(`class`: Class): TypeSpec {
            val modifiers = `class`.modifiers.map(ClassModifier::toPoetModifier)
            return TypeSpec.classBuilder(`class`.name).apply {
                addDocumentation(`class`.documentation)
                addAnnotations(`class`.annotations)
                addModifiers(modifiers)
                addTypeVariables(`class`.typeVariables)
                addPrimaryConstructor(`class`.primaryConstructor)
                addSuperClass(`class`.superClass)
                addSuperInterfaces(`class`.superInterfaces)
                addProperties(`class`.properties)
                addInitializer(`class`.initializer)
                addFunctions(`class`.functions)
                addObjects(`class`.objects)
                addCompanionObject(`class`.companionObject)
                addClasses(`class`.classes)
                addValueClasses(`class`.valueClasses)
                addEnumClasses(`class`.enumClasses)
                addDataClasses(`class`.dataClasses)
                addAnnotationClasses(`class`.annotationClasses)
                addInterfaces(`class`.interfaces)
                addFunctionalInterfaces(`class`.functionalInterfaces)
            }.build()
        }
    }
}