package com.bselzer.ktx.openapi.client.model.type.enumclass

import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumConstant
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.value.OpenApiString

/**
 * Resolves an enumeration declared by the enum property.
 */
class EnumResolver : EnumClassResolver {
    override fun canResolve(context: EnumContext): Boolean = with(context) {
        schema.enum.filterIsInstance<OpenApiString>().any()
    }

    override fun resolve(context: EnumContext): EnumClass = with(context) {
        EnumClass(
            name = name,
            documentation = schema.description?.toDocumentation(),
            constants = schema.enum.map { enum ->
                EnumConstant(
                    name = enum.toString(),
                    documentation = schema.description?.toDocumentation()
                )
            }
        )
    }
}