package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.client.type.name.ClassName
import com.bselzer.ktx.openapi.client.type.name.ParameterizedTypeName
import com.bselzer.ktx.openapi.client.type.name.TypeName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class ListPropertyHandler(
    propertyHandlerResolver: PropertyHandlerResolver
) : NestedPropertyHandler(propertyHandlerResolver) {
    override fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean {
        val hasType = schema.types.contains(OpenApiSchemaType.ARRAY)
        val hasNestedSchema = schema.items != null
        return hasType && hasNestedSchema
    }

    // TODO maxItems, minItems, ... with setter
    override fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): PropertyOutput {
        fun nestedSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(schema.items) { "Expected a list to have an items schema." }
            return nestedSchemaType(nestedSchemaReference, references)
        }

        return PropertyOutput(
            typeName = ParameterizedTypeName(
                root = ClassName.LIST,
                parameters = listOf(nestedSchemaType())
            ),
            nullable = schema.isNullable,
            description = schema.description?.toString(),
            instantiation = when {
                schema.isNullable -> "null"
                else -> "listOf()"
            }
        )
    }
}