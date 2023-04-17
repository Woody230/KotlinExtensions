package com.bselzer.ktx.codegen.model.type.name

data class WildcardTypeName(
    val type: TypeName,
    val variance: Variance
) : TypeName