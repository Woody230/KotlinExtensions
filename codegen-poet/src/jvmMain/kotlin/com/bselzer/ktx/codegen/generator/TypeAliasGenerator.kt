package com.bselzer.ktx.codegen.generator

import com.bselzer.ktx.codegen.model.type.alias.TypeAlias
import com.bselzer.ktx.codegen.model.type.alias.TypeAliasModifier
import com.bselzer.ktx.codegen.model.type.alias.toPoetModifier
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.codegen.model.type.toPoetTypeName
import com.bselzer.ktx.codegen.model.type.toPoetTypeVariableName
import com.squareup.kotlinpoet.TypeAliasSpec

interface TypeAliasGenerator {
    fun build(typeAlias: TypeAlias): TypeAliasSpec

    companion object : TypeAliasGenerator {
        override fun build(typeAlias: TypeAlias): TypeAliasSpec {
            val type = typeAlias.type.toPoetTypeName()
            val annotations = typeAlias.annotations.map(AnnotationGenerator::build)
            val modifiers = typeAlias.modifiers.map(TypeAliasModifier::toPoetModifier)
            val typeVariables = typeAlias.typeVariables.map(TypeVariableName::toPoetTypeVariableName)
            return TypeAliasSpec.builder(typeAlias.name, type).apply {
                addAnnotations(annotations)
                addModifiers(modifiers)
                addTypeVariables(typeVariables)

                typeAlias.documentation?.let { documentation -> addKdoc(documentation.toString()) }
            }.build()
        }
    }
}