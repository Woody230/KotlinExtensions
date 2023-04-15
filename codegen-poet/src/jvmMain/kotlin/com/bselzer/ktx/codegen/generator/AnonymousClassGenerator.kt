package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.extensions.addAnnotations
import com.bselzer.ktx.codegen.model.extensions.addDocumentation
import com.bselzer.ktx.codegen.model.extensions.addFunctions
import com.bselzer.ktx.codegen.model.extensions.addInitializer
import com.bselzer.ktx.codegen.model.extensions.addProperties
import com.bselzer.ktx.codegen.model.extensions.addSuperClass
import com.bselzer.ktx.codegen.model.extensions.addSuperInterfaces
import com.bselzer.ktx.codegen.model.type.`class`.anonymous.AnonymousClass
import com.squareup.kotlinpoet.TypeSpec

interface AnonymousClassGenerator {
    fun build(anonymousClass: AnonymousClass): TypeSpec

    companion object : AnonymousClassGenerator {
        override fun build(anonymousClass: AnonymousClass): TypeSpec {
            return TypeSpec.anonymousClassBuilder().apply {
                addAnnotations(anonymousClass.annotations)
                addDocumentation(anonymousClass.documentation)
                addSuperClass(anonymousClass.superClass)
                addSuperInterfaces(anonymousClass.superInterfaces)
                addInitializer(anonymousClass.initializer)
                addProperties(anonymousClass.properties)
                addFunctions(anonymousClass.functions)
            }.build()
        }
    }
}