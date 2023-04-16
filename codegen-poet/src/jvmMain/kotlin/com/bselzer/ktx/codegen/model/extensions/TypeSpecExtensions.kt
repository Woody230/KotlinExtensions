package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.generator.AnnotationClassGenerator
import com.bselzer.ktx.codegen.generator.AnnotationGenerator
import com.bselzer.ktx.codegen.generator.ClassGenerator
import com.bselzer.ktx.codegen.generator.CompanionObjectGenerator
import com.bselzer.ktx.codegen.generator.ConstructorGenerator
import com.bselzer.ktx.codegen.generator.DataClassGenerator
import com.bselzer.ktx.codegen.generator.EnumClassGenerator
import com.bselzer.ktx.codegen.generator.FunctionGenerator
import com.bselzer.ktx.codegen.generator.FunctionalInterfaceGenerator
import com.bselzer.ktx.codegen.generator.InterfaceGenerator
import com.bselzer.ktx.codegen.generator.ObjectGenerator
import com.bselzer.ktx.codegen.generator.PropertyGenerator
import com.bselzer.ktx.codegen.generator.ValueClassGenerator
import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`class`.Class
import com.bselzer.ktx.codegen.model.type.`class`.annotation.AnnotationClass
import com.bselzer.ktx.codegen.model.type.`class`.data.DataClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumConstant
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumConstructor
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.`interface`.Interface
import com.bselzer.ktx.codegen.model.type.`interface`.functional.FunctionalInterface
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.`object`.Object
import com.bselzer.ktx.codegen.model.type.`object`.companion.CompanionObject
import com.bselzer.ktx.codegen.model.type.`super`.`class`.SuperClass
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.DelegableSuperInterface
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.SuperInterface
import com.squareup.kotlinpoet.TypeSpec

internal fun TypeSpec.Builder.addSuperInterfaces(superInterfaces: Collection<SuperInterface>) {
    for (superInterface in superInterfaces) {
        addSuperInterface(superInterface)
    }
}

internal fun TypeSpec.Builder.addSuperInterface(superInterface: SuperInterface) {
    val type = superInterface.type.toPoetTypeName()
    val delegate = if (superInterface is DelegableSuperInterface) superInterface.delegate else null
    if (delegate == null) {
        addSuperinterface(type)
    } else {
        addSuperinterface(type, delegate.toString())
    }
}

internal fun TypeSpec.Builder.addSuperClass(superClass: SuperClass?) {
    if (superClass == null) {
        return
    }

    superclass(superClass.type.toPoetTypeName())
    superClass.arguments.forEach { argument -> addSuperclassConstructorParameter(argument.toString()) }
}

internal fun TypeSpec.Builder.addDocumentation(documentation: Documentation?) {
    if (documentation == null) {
        return
    }

    addKdoc(documentation.toString())
}

internal fun TypeSpec.Builder.addAnnotations(annotations: Collection<Annotation>) {
    for (annotation in annotations.map(AnnotationGenerator::build)) {
        addAnnotation(annotation)
    }
}

internal fun TypeSpec.Builder.addProperties(properties: Collection<Property>) {
    for (property in properties.map(PropertyGenerator::build)) {
        addProperty(property)
    }
}

internal fun TypeSpec.Builder.addInitializer(initializer: CodeBlock?) {
    if (initializer == null) {
        return
    }

    val codeBlock = com.squareup.kotlinpoet.CodeBlock.of(initializer.toString())
    addInitializerBlock(codeBlock)
}

internal fun TypeSpec.Builder.addFunctions(functions: Collection<Function>) {
    for (function in functions.map(FunctionGenerator::build)) {
        addFunction(function)
    }
}

internal fun TypeSpec.Builder.addObjects(objects: Collection<Object>) {
    for (`object` in objects.map(ObjectGenerator::build)) {
        addType(`object`)
    }
}

internal fun TypeSpec.Builder.addCompanionObject(companionObject: CompanionObject?) {
    if (companionObject == null) {
        return
    }

    addType(CompanionObjectGenerator.build(companionObject))
}

internal fun TypeSpec.Builder.addClasses(classes: Collection<Class>) {
    for (`class` in classes.map(ClassGenerator::build)) {
        addType(`class`)
    }
}

internal fun TypeSpec.Builder.addValueClasses(valueClasses: Collection<ValueClass>) {
    for (valueClass in valueClasses.map(ValueClassGenerator::build)) {
        addType(valueClass)
    }
}

internal fun TypeSpec.Builder.addEnumClasses(enumClasses: Collection<EnumClass>) {
    for (enumClass in enumClasses.map(EnumClassGenerator::build)) {
        addType(enumClass)
    }
}

internal fun TypeSpec.Builder.addDataClasses(dataClasses: Collection<DataClass>) {
    for (dataClass in dataClasses.map(DataClassGenerator::build)) {
        addType(dataClass)
    }
}

internal fun TypeSpec.Builder.addAnnotationClasses(annotationClasses: Collection<AnnotationClass>) {
    for (annotationClass in annotationClasses.map(AnnotationClassGenerator::build)) {
        addType(annotationClass)
    }
}

internal fun TypeSpec.Builder.addInterfaces(interfaces: Collection<Interface>) {
    for (`interface` in interfaces.map(InterfaceGenerator::build)) {
        addType(`interface`)
    }
}

internal fun TypeSpec.Builder.addFunctionalInterfaces(functionalInterfaces: Collection<FunctionalInterface>) {
    for (functionalInterface in functionalInterfaces.map(FunctionalInterfaceGenerator::build)) {
        addType(functionalInterface)
    }
}

internal fun TypeSpec.Builder.addPrimaryConstructor(constructor: Constructor?) {
    if (constructor == null) {
        return
    }

    primaryConstructor(ConstructorGenerator.build(constructor))
}

internal fun TypeSpec.Builder.addTypeVariables(typeVariables: Collection<TypeVariableName>) {
    for (typeVariable in typeVariables.map(TypeVariableName::toPoetTypeVariableName)) {
        addTypeVariable(typeVariable)
    }
}

internal fun TypeSpec.Builder.addEnumConstructor(enumConstructor: EnumConstructor?) {
    if (enumConstructor == null) {
        return
    }

    val constructor = object : Constructor {
        override val parameters: Collection<Parameter> = enumConstructor.parameters
        override val modifiers: Set<ConstructorModifier> = emptySet()
        override val body: CodeBlock? = enumConstructor.body
        override val reference: ConstructorReference? = null
    }

    primaryConstructor(ConstructorGenerator.build(constructor))
}

internal fun TypeSpec.Builder.addEnumConstants(enumConstants: Collection<EnumConstant>) {
    for (enumConstant in enumConstants) {
        addEnumConstant(enumConstant)
    }
}

internal fun TypeSpec.Builder.addEnumConstant(enumConstant: EnumConstant) {
    val arguments = enumConstant.arguments.map(CodeBlock::toString)
    val type = TypeSpec.anonymousClassBuilder().apply {
        addAnnotations(enumConstant.annotations)
        arguments.forEach { argument -> addSuperclassConstructorParameter(argument) }
    }.build()

    addEnumConstant(enumConstant.name, type)
}