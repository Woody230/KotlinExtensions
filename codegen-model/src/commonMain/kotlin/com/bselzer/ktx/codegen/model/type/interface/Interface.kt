package com.bselzer.ktx.codegen.model.type.`interface`

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.SuperInterface

interface Interface {
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val name: String
    val modifiers: Set<InterfaceModifier>
    val typeVariables: Collection<TypeVariableName>
    val properties: Collection<Property>
    val superInterfaces: Collection<SuperInterface>
    val functions: Collection<Function>
    val objects: Collection<Object>
    val companionObject: CompanionObject?
    val classes: Collection<Class>
    val valueClasses: Collection<ValueClass>
    val enumClasses: Collection<EnumClass>
    val dataClasses: Collection<DataClass>
    val annotationClasses: Collection<AnnotationClass>
    val interfaces: Collection<Interface>
    val functionalInterfaces: Collection<FunctionalInterface>
}