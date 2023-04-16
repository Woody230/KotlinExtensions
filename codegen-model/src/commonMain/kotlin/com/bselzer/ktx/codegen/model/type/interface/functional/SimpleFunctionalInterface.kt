package com.bselzer.ktx.codegen.model.type.`interface`.functional

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.SuperInterface

data class SimpleFunctionalInterface(
    override val name: String,
    override val superInterfaces: Collection<SuperInterface> = emptyList(),
    override val properties: Collection<Property> = emptyList(),
    override val functions: Collection<Function> = emptyList(),
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val modifiers: Set<FunctionalInterfaceModifier> = emptySet(),
    override val typeVariables: Collection<TypeVariableName> = emptyList(),
    override val objects: Collection<Object> = emptyList(),
    override val companionObject: CompanionObject? = null,
    override val classes: Collection<Class> = emptyList(),
    override val valueClasses: Collection<ValueClass> = emptyList(),
    override val enumClasses: Collection<EnumClass> = emptyList(),
    override val dataClasses: Collection<DataClass> = emptyList(),
    override val annotationClasses: Collection<AnnotationClass> = emptyList(),
    override val interfaces: Collection<Interface> = emptyList(),
    override val functionalInterfaces: Collection<FunctionalInterface> = emptyList()
) : FunctionalInterface