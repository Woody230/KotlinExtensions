package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.file.source.SourceFile
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec

interface SourceFileGenerator {
    fun build(sourceFile: SourceFile): FileSpec

    companion object : SourceFileGenerator {
        override fun build(sourceFile: SourceFile): FileSpec {
            val annotations = sourceFile.annotations.map(AnnotationGenerator::build)
            val annotationClasses = sourceFile.annotationClasses.map(AnnotationClassGenerator::build)
            val dataClasses = sourceFile.dataClasses.map(DataClassGenerator::build)
            val enumClasses = sourceFile.enumClasses.map(EnumClassGenerator::build)
            val valueClasses = sourceFile.valueClasses.map(ValueClassGenerator::build)
            val classes = sourceFile.classes.map(ClassGenerator::build)
            val functionalInterfaces = sourceFile.functionalInterfaces.map(FunctionalInterfaceGenerator::build)
            val interfaces = sourceFile.interfaces.map(InterfaceGenerator::build)
            val objects = sourceFile.objects.map(ObjectGenerator::build)
            val functions = sourceFile.functions.map(FunctionGenerator::build)
            val properties = sourceFile.properties.map(PropertyGenerator::build)
            val typeAliases = sourceFile.typeAliases.map(TypeAliasGenerator::build)
            return FileSpec.builder(sourceFile.packageName, sourceFile.name).apply {
                sourceFile.documentation?.let { documentation -> addFileComment(documentation.toString()) }

                typeAliases.forEach { typeAlias -> addTypeAlias(typeAlias) }
                properties.forEach { property -> addProperty(property) }
                functions.forEach { function -> addFunction(function) }

                annotations.forEach { annotation -> addAnnotation(annotation) }
                annotationClasses.forEach { annotationClass -> addType(annotationClass) }
                dataClasses.forEach { dataClass -> addType(dataClass) }
                enumClasses.forEach { enumClass -> addType(enumClass) }
                valueClasses.forEach { valueClass -> addType(valueClass) }
                classes.forEach { `class` -> addType(`class`) }
                functionalInterfaces.forEach { functionalInterface -> addType(functionalInterface) }
                interfaces.forEach { `interface` -> addType(`interface`) }
                objects.forEach { `object` -> addType(`object`) }

                sourceFile.imports.forEach { import ->
                    val alias = import.alias
                    if (alias == null) {
                        addImport(import.name)
                    } else {
                        addAliasedImport(ClassName("", import.name), alias)
                    }
                }
            }.build()
        }
    }
}