package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.addAnnotationClasses
import com.bselzer.ktx.codegen.model.extensions.addAnnotations
import com.bselzer.ktx.codegen.model.extensions.addClasses
import com.bselzer.ktx.codegen.model.extensions.addCompanionObject
import com.bselzer.ktx.codegen.model.extensions.addDataClasses
import com.bselzer.ktx.codegen.model.extensions.addDocumentation
import com.bselzer.ktx.codegen.model.extensions.addEnumClasses
import com.bselzer.ktx.codegen.model.extensions.addFunctionalInterfaces
import com.bselzer.ktx.codegen.model.extensions.addInterfaces
import com.bselzer.ktx.codegen.model.extensions.addObjects
import com.bselzer.ktx.codegen.model.extensions.addPrimaryConstructor
import com.bselzer.ktx.codegen.model.extensions.addValueClasses
import com.bselzer.ktx.codegen.model.extensions.toPoetModifier
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClassModifier
import com.squareup.kotlinpoet.TypeSpec

interface AnnotationClassGenerator {
    fun build(annotationClass: AnnotationClass): TypeSpec

    companion object : AnnotationClassGenerator {
        override fun build(annotationClass: AnnotationClass): TypeSpec {
            val modifiers = annotationClass.modifiers.map(AnnotationClassModifier::toPoetModifier)
            return TypeSpec.annotationBuilder(annotationClass.name).apply {
                addDocumentation(annotationClass.documentation)
                addAnnotations(annotationClass.annotations)
                addModifiers(modifiers)
                addPrimaryConstructor(annotationClass.primaryConstructor)
                addObjects(annotationClass.objects)
                addCompanionObject(annotationClass.companionObject)
                addClasses(annotationClass.classes)
                addValueClasses(annotationClass.valueClasses)
                addEnumClasses(annotationClass.enumClasses)
                addDataClasses(annotationClass.dataClasses)
                addAnnotationClasses(annotationClass.annotationClasses)
                addInterfaces(annotationClass.interfaces)
                addFunctionalInterfaces(annotationClass.functionalInterfaces)
            }.build()
        }
    }
}