package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.addAnnotationClasses
import com.bselzer.ktx.codegen.model.extensions.addAnnotations
import com.bselzer.ktx.codegen.model.extensions.addClasses
import com.bselzer.ktx.codegen.model.extensions.addDataClasses
import com.bselzer.ktx.codegen.model.extensions.addDocumentation
import com.bselzer.ktx.codegen.model.extensions.addEnumClasses
import com.bselzer.ktx.codegen.model.extensions.addFunctionalInterfaces
import com.bselzer.ktx.codegen.model.extensions.addFunctions
import com.bselzer.ktx.codegen.model.extensions.addInitializer
import com.bselzer.ktx.codegen.model.extensions.addInterfaces
import com.bselzer.ktx.codegen.model.extensions.addObjects
import com.bselzer.ktx.codegen.model.extensions.addProperties
import com.bselzer.ktx.codegen.model.extensions.addSuperClass
import com.bselzer.ktx.codegen.model.extensions.addSuperInterfaces
import com.bselzer.ktx.codegen.model.extensions.addValueClasses
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObjectModifier
import com.squareup.kotlinpoet.TypeSpec

interface CompanionObjectGenerator {
    fun build(companionObject: CompanionObject): TypeSpec

    companion object : CompanionObjectGenerator {
        override fun build(companionObject: CompanionObject): TypeSpec {
            val modifiers = companionObject.modifiers.map(CompanionObjectModifier::toPoetModifier)
            return TypeSpec.companionObjectBuilder(companionObject.name).apply {
                addDocumentation(companionObject.documentation)
                addAnnotations(companionObject.annotations)
                addModifiers(modifiers)
                addSuperClass(companionObject.superClass)
                addSuperInterfaces(companionObject.superInterfaces)
                addProperties(companionObject.properties)
                addInitializer(companionObject.initializer)
                addFunctions(companionObject.functions)
                addObjects(companionObject.objects)
                addClasses(companionObject.classes)
                addValueClasses(companionObject.valueClasses)
                addEnumClasses(companionObject.enumClasses)
                addDataClasses(companionObject.dataClasses)
                addAnnotationClasses(companionObject.annotationClasses)
                addInterfaces(companionObject.interfaces)
                addFunctionalInterfaces(companionObject.functionalInterfaces)
            }.build()
        }
    }
}