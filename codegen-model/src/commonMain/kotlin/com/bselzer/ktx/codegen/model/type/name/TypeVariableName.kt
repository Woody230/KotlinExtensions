package com.bselzer.ktx.codegen.model.type.name

data class TypeVariableName(
    val name: String,
    val bounds: Collection<TypeName>,
    val variance: Variance?,
    val reified: Boolean,
) : TypeName {
    override fun toString(): String = name
}