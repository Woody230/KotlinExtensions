package com.bselzer.ktx.codegen.model.type

data class ParameterizedTypeName(
    val root: ClassName,
    val parameters: List<TypeName>
) : TypeName {
    override fun toString(): String = buildString {
        append(root)

        if (parameters.isEmpty()) {
            return@buildString
        }

        append('<')
        append(parameters.joinToString(separator = ",", transform = TypeName::toString))
        append('>')
    }
}