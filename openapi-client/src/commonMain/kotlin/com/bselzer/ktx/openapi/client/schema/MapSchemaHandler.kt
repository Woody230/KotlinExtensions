package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.client.type.ParameterizedTypeName
import com.bselzer.ktx.openapi.client.type.TypeName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class MapSchemaHandler(
    schemaResolver: SchemaResolver
) : NestedSchemaHandler(schemaResolver) {
    override fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean {
        val hasType = schema.types.contains(OpenApiSchemaType.OBJECT)
        val hasNestedSchema = schema.additionalProperties != null
        return hasType && hasNestedSchema
    }

    override fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): SchemaOutput {
        val nestedType = nestedSchemaType(schema, references)
        return SchemaOutput(
            typeName = ParameterizedTypeName(
                root = ClassName.MAP,
                parameters = listOf(ClassName.STRING, nestedType)
            ),
            nullable = schema.isNullable,
            description = schema.description?.toString(),
            instantiation = when {
                schema.isNullable -> "null"
                else -> "mapOf()"
            }
        )
    }

    private fun nestedSchemaType(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): TypeName {
        val nestedSchemaReference = requireNotNull(schema.additionalProperties) { "Expected a map to have an additionalProperties schema." }
        return nestedSchemaType(nestedSchemaReference, references)
    }
}