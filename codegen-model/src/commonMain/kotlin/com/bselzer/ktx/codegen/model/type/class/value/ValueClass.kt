package com.bselzer.ktx.codegen.model.type.`class`.value

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.AccessorProperty
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.DelegableSuperInterface

interface ValueClass {
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val name: String
    val modifiers: Set<ValueClassModifier>
    val typeVariables: Collection<TypeVariableName>
    val primaryConstructor: ValueClassConstructor
    val superInterfaces: Collection<DelegableSuperInterface>
    val properties: Collection<AccessorProperty>
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