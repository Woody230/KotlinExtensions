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
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.ObjectModifier
import com.squareup.kotlinpoet.TypeSpec

interface ObjectGenerator {
    fun build(`object`: Object): TypeSpec

    companion object : ObjectGenerator {
        override fun build(`object`: Object): TypeSpec {
            val modifiers = `object`.modifiers.map(ObjectModifier::toPoetModifier)
            return TypeSpec.objectBuilder(`object`.name).apply {
                addDocumentation(`object`.documentation)
                addAnnotations(`object`.annotations)
                addModifiers(modifiers)
                addSuperClass(`object`.superClass)
                addSuperInterfaces(`object`.superInterfaces)
                addProperties(`object`.properties)
                addInitializer(`object`.initializer)
                addFunctions(`object`.functions)
                addObjects(`object`.objects)
                addClasses(`object`.classes)
                addValueClasses(`object`.valueClasses)
                addEnumClasses(`object`.enumClasses)
                addDataClasses(`object`.dataClasses)
                addAnnotationClasses(`object`.annotationClasses)
                addInterfaces(`object`.interfaces)
                addFunctionalInterfaces(`object`.functionalInterfaces)
            }.build()
        }
    }
}