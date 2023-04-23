package com.bselzer.ktx.codegen.model.type.`class`

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject
import com.bselzer.ktx.codegen.model.type.`super`.`class`.SuperClass
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.DelegableSuperInterface

data class Class(
    val name: String,
    val primaryConstructor: Constructor? = null,
    val superClass: SuperClass? = null,
    val superInterfaces: Collection<DelegableSuperInterface> = emptyList(),
    val properties: Collection<Property> = emptyList(),
    val functions: Collection<Function> = emptyList(),
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val modifiers: Set<ClassModifier> = setOf(),
    val typeVariables: Collection<TypeVariableName> = emptyList(),
    val initializer: CodeBlock? = null,
    val objects: Collection<Object> = emptyList(),
    val companionObject: CompanionObject? = null,
    val classes: Collection<Class> = emptyList(),
    val valueClasses: Collection<ValueClass> = emptyList(),
    val enumClasses: Collection<EnumClass> = emptyList(),
    val dataClasses: Collection<DataClass> = emptyList(),
    val annotationClasses: Collection<AnnotationClass> = emptyList(),
    val interfaces: Collection<Interface> = emptyList(),
    val functionalInterfaces: Collection<FunctionalInterface> = emptyList()
)