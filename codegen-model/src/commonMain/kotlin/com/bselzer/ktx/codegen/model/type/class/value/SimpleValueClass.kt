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

data class SimpleValueClass(
    override val name: String,
    override val primaryConstructor: ValueClassConstructor,
    override val superInterfaces: Collection<DelegableSuperInterface> = emptyList(),
    override val properties: Collection<AccessorProperty> = emptyList(),
    override val functions: Collection<Function> = emptyList(),
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val modifiers: Set<ValueClassModifier> = emptySet(),
    override val typeVariables: Collection<TypeVariableName> = emptyList(),
    override val initializer: CodeBlock? = null,
    override val objects: Collection<Object> = emptyList(),
    override val companionObject: CompanionObject? = null,
    override val classes: Collection<Class> = emptyList(),
    override val valueClasses: Collection<ValueClass> = emptyList(),
    override val enumClasses: Collection<EnumClass> = emptyList(),
    override val dataClasses: Collection<DataClass> = emptyList(),
    override val annotationClasses: Collection<AnnotationClass> = emptyList(),
    override val interfaces: Collection<Interface> = emptyList(),
    override val functionalInterfaces: Collection<FunctionalInterface> = emptyList()
) : ValueClass