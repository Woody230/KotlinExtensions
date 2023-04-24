package com.bselzer.ktx.openapi.client.model.enum

import com.bselzer.ktx.codegen.model.annotation.SerialName
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumConstant
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.value.OpenApiString

/**
 * Resolves an enumeration declared by the const property.
 */
class ConstantEnumResolver : EnumResolver {
    override fun canResolve(context: EnumContext): Boolean = with(context) {
        schema.constants().any()
    }

    override fun resolve(context: EnumContext): EnumClass = with(context) {
        EnumClass(
            name = name,
            documentation = schema.description?.toDocumentation(),
            constants = schema.constants().map { schema ->
                EnumConstant(
                    name = schema.title ?: schema.const.toString(),
                    documentation = schema.description?.toDocumentation(),
                    annotations = buildList {
                        if (schema.title != null) {
                            add(SerialName(schema.const.toString()))
                        }
                    }
                )
            }
        )
    }

    /**
     * Collects the schemas (including this one) that define an enum constant.
     */
    private fun OpenApiSchema.constants(): Collection<OpenApiSchema> {
        val schemas = allOf.filter { schema -> schema.const is OpenApiString }

        if (const !is OpenApiString) {
            return schemas
        }

        return schemas + this
    }
}