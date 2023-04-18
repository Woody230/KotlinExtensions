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

data class CopyableSourceFile(
    override val name: String,
    override val packageName: String,
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
    override val annotationClasses: Collection<AnnotationClass> = emptyList(),
    override val dataClasses: Collection<DataClass> = emptyList(),
    override val enumClasses: Collection<EnumClass> = emptyList(),
    override val valueClasses: Collection<ValueClass> = emptyList(),
    override val classes: Collection<Class> = emptyList(),
    override val functionalInterfaces: Collection<FunctionalInterface> = emptyList(),
    override val interfaces: Collection<Interface> = emptyList(),
    override val objects: Collection<Object> = emptyList(),
    override val functions: Collection<Function> = emptyList(),
    override val properties: Collection<Property> = emptyList(),
    override val typeAliases: Collection<TypeAlias> = emptyList(),
    override val imports: Collection<Import> = emptyList()
) : SourceFile