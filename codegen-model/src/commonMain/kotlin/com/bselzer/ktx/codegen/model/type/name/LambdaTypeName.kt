package com.bselzer.ktx.codegen.model.type.name

import com.bselzer.ktx.codegen.model.parameter.Parameter

data class LambdaTypeName(
    val receiver: TypeName? = null,
    val parameters: Collection<Parameter> = emptyList(),
    val returns: TypeName? = null,
    val contextReceivers: Collection<TypeName> = emptyList()
) : TypeName