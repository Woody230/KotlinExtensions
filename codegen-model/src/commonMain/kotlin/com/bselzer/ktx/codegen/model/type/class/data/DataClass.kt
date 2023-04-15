package com.bselzer.ktx.codegen.model.type.`class`.data

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.DelegableSuperInterface
import com.bselzer.ktx.codegen.model.type.SuperClass
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject

interface DataClass {
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val name: String
    val modifiers: Set<DataClassModifier>
    val typeVariables: Collection<TypeVariableName>
    val primaryConstructor: Constructor?
    val superClass: SuperClass?
    val superInterfaces: Collection<DelegableSuperInterface>
    val properties: Collection<Property>
    val initializer: CodeBlock?
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