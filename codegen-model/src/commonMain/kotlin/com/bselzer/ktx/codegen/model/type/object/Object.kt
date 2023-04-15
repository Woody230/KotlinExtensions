package com.bselzer.ktx.codegen.model.type.`object`

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.DelegableSuperInterface
import com.bselzer.ktx.codegen.model.type.SuperClass
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface

interface Object {
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val name: String
    val modifiers: Set<ObjectModifier>
    val superClass: SuperClass?
    val superInterfaces: Collection<DelegableSuperInterface>
    val properties: Collection<Property>
    val initializer: CodeBlock?
    val functions: Collection<Function>
    val objects: Collection<Object>
    val classes: Collection<Class>
    val valueClasses: Collection<ValueClass>
    val enumClasses: Collection<EnumClass>
    val dataClasses: Collection<DataClass>
    val annotationClasses: Collection<AnnotationClass>
    val interfaces: Collection<Interface>
    val functionalInterfaces: Collection<FunctionalInterface>
}
