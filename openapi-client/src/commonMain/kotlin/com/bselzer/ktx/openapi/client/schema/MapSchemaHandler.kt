package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.internal.ExtensionConstants
import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.client.type.ParameterizedTypeName
import com.bselzer.ktx.openapi.client.type.TypeName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import com.bselzer.ktx.openapi.model.value.OpenApiMap
import com.bselzer.ktx.openapi.serialization.ReferenceOfOpenApiSchemaSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class MapSchemaHandler(
    schemaResolver: SchemaResolver
) : NestedSchemaHandler(schemaResolver) {
    override fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean {
        val hasType = schema.types.contains(OpenApiSchemaType.OBJECT)
        val hasNestedSchema = schema.additionalProperties != null
        return hasType && hasNestedSchema
    }

    override fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): SchemaOutput {
        fun keySchemaType(): TypeName {
            // If the extension is not present, then the base assumption is that keys must be Strings.
            // If the extension is present, then assume that the key's value is a schema or a reference to a schema.
            val extension = schema.extensions[ExtensionConstants.KEY] ?: return ClassName.STRING
            require(extension is OpenApiMap) { "Expected the key to be in the form of an OpenApiMap." }

            // TODO model to be included in generation when it is a schema and not just a reference
            val serialized = Json.encodeToJsonElement(extension.value)
            val nestedSchemaReference = Json.decodeFromJsonElement(ReferenceOfOpenApiSchemaSerializer, serialized)
            return nestedSchemaType(nestedSchemaReference, references)
        }

        fun valueSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(schema.additionalProperties) { "Expected a map to have an additionalProperties schema." }
            return nestedSchemaType(nestedSchemaReference, references)
        }

        return SchemaOutput(
            typeName = ParameterizedTypeName(
                root = ClassName.MAP,
                parameters = listOf(keySchemaType(), valueSchemaType())
            ),
            nullable = schema.isNullable,
            description = schema.description?.toString(),
            instantiation = when {
                schema.isNullable -> "null"
                else -> "mapOf()"
            }
        )
    }
}