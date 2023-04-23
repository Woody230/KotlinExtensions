package com.bselzer.ktx.codegen.model.type.`class`.annotation

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject

data class AnnotationClass(
    val name: String,
    val primaryConstructor: Constructor? = null,
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val modifiers: Set<AnnotationClassModifier> = setOf(),
    val objects: Collection<Object> = emptyList(),
    val companionObject: CompanionObject? = null,
    val classes: Collection<Class> = emptyList(),
    val valueClasses: Collection<ValueClass> = emptyList(),
    val enumClasses: Collection<EnumClass> = emptyList(),
    val dataClasses: Collection<DataClass> = emptyList(),
    val annotationClasses: Collection<AnnotationClass> = emptyList(),
    val interfaces: Collection<Interface> = emptyList(),
    val functionalInterfaces: Collection<FunctionalInterface> = emptyList()
) {
    override fun toString(): String = name
}