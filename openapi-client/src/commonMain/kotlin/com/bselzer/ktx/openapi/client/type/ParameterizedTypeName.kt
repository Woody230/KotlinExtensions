package com.bselzer.ktx.openapi.client.type

class ParameterizedTypeName(
    private val root: ClassName,
    private val parameters: List<TypeName>
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