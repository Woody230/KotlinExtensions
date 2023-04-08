package com.bselzer.ktx.openapi.client.type.name

class ParameterizedTypeName(
    internal val root: ClassName,
    internal val parameters: List<TypeName>
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