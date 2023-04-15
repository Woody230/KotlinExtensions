package com.bselzer.ktx.codegen.model.file.source

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.file.import.Import
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.alias.TypeAlias
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.`object`.Object

interface SourceFile {
    val name: String
    val packageName: String
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val annotationClasses: Collection<AnnotationClass>
    val dataClasses: Collection<DataClass>
    val enumClasses: Collection<EnumClass>
    val valueClasses: Collection<ValueClass>
    val classes: Collection<Class>
    val functionalInterfaces: Collection<FunctionalInterface>
    val interfaces: Collection<Interface>
    val objects: Collection<Object>
    val functions: Collection<Function>
    val properties: Collection<Property>
    val typeAliases: Collection<TypeAlias>
    val imports: Collection<Import>
}