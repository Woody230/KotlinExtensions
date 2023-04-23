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

data class SourceFile(
    val name: String,
    val packageName: String,
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
    val annotationClasses: Collection<AnnotationClass> = emptyList(),
    val dataClasses: Collection<DataClass> = emptyList(),
    val enumClasses: Collection<EnumClass> = emptyList(),
    val valueClasses: Collection<ValueClass> = emptyList(),
    val classes: Collection<Class> = emptyList(),
    val functionalInterfaces: Collection<FunctionalInterface> = emptyList(),
    val interfaces: Collection<Interface> = emptyList(),
    val objects: Collection<Object> = emptyList(),
    val functions: Collection<Function> = emptyList(),
    val properties: Collection<Property> = emptyList(),
    val typeAliases: Collection<TypeAlias> = emptyList(),
    val imports: Collection<Import> = emptyList()
) {
    override fun toString(): String = name
}