package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.client.type.ParameterizedTypeName
import com.bselzer.ktx.openapi.client.type.TypeName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class ListSchemaHandler(
    private val schemaResolver: SchemaResolver
) : SchemaHandler {
    override fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean {
        val hasType = schema.types.contains(OpenApiSchemaType.ARRAY)
        val hasNestedSchema = schema.items != null
        return hasType && hasNestedSchema
    }

    // TODO maxItems, minItems, ... with setter
    override fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): SchemaOutput {
        val nestedType = nestedSchemaType(schema, references)
        return SchemaOutput(
            typeName = ParameterizedTypeName(
                root = ClassName.LIST,
                parameters = listOf(nestedType)
            ),
            nullable = schema.isNullable,
            description = schema.description?.toString(),
            instantiation = when {
                schema.isNullable -> "null"
                else -> "listOf()"
            }
        )
    }

    private fun nestedSchemaType(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): TypeName {
        val nestedSchemaReference = requireNotNull(schema.items) { "Expected an array to have an items schema." }

        val nestedSchema = nestedSchemaReference.resolve(
            onValue = { nestedSchema -> nestedSchema },
            onReference = { reference -> references[reference.`$ref`.componentName] }
        )
        requireNotNull(nestedSchema) { "Unable to resolve the reference to the nested schema." }

        val nestedSchemaOutput = schemaResolver.handler(nestedSchema, references).resolve(nestedSchema, references)
        return nestedSchemaOutput.typeName
    }
}