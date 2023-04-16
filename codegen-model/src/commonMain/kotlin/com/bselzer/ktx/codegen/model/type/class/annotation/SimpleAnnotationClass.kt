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

data class SimpleAnnotationClass(
    override val name: String,
    override val primaryConstructor: Constructor? = null,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val modifiers: Set<AnnotationClassModifier> = setOf(),
    override val objects: Collection<Object> = emptyList(),
    override val companionObject: CompanionObject? = null,
    override val classes: Collection<Class> = emptyList(),
    override val valueClasses: Collection<ValueClass> = emptyList(),
    override val enumClasses: Collection<EnumClass> = emptyList(),
    override val dataClasses: Collection<DataClass> = emptyList(),
    override val annotationClasses: Collection<AnnotationClass> = emptyList(),
    override val interfaces: Collection<Interface> = emptyList(),
    override val functionalInterfaces: Collection<FunctionalInterface> = emptyList()
) : AnnotationClass