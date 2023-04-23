package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.declaration.InitializedPropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.name.ParameterizedTypeName
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.openapi.client.internal.ExtensionConstants
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import com.bselzer.ktx.openapi.serialization.ReferenceOfOpenApiSchemaSerializer
import kotlinx.serialization.json.Json

open class MapPropertyResolver(
    private val keyResolver: PropertyResolver,
    private val valueResolver: PropertyResolver
) : NestedPropertyResolver() {
    override fun canResolve(input: PropertyContext): Boolean = with(input) {
        val hasType = schema.types.contains(OpenApiSchemaType.OBJECT)
        val hasNestedSchema = schema.additionalProperties != null
        val canNestedResolve = keyResolver.canResolve(input) && valueResolver.canResolve(input)
        return hasType && hasNestedSchema && canNestedResolve
    }

    override fun resolve(input: PropertyContext): Property = with(input) {
        fun keySchemaType(): TypeName {
            // If the extension is not present, then the base assumption is that keys must be Strings.
            val extension = schema.extensions[ExtensionConstants.KEY] ?: return ClassName.STRING

            // If the extension is present, then assume that the key's value is a schema or a reference to a schema.
            val nestedSchemaReference = Json.decodeFromJsonElement(ReferenceOfOpenApiSchemaSerializer, extension)
            return nestedProperty(nestedSchemaReference, input).type
        }

        fun valueSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(schema.additionalProperties) { "Expected a map to have an additionalProperties schema." }
            return nestedProperty(nestedSchemaReference, input).type
        }

        return Property(
            type = ParameterizedTypeName(
                root = ClassName.MAP.copy(nullable = schema.isNullable),
                parameters = listOf(keySchemaType(), valueSchemaType())
            ),
            name = input.name,
            documentation = schema.description?.toDocumentation(),
            declaration = InitializedPropertyDeclaration(
                mutable = false,
                initializer = when {
                    schema.isNullable -> "null"
                    else -> "mapOf()"
                }.toCodeBlock()
            )
        )
    }
}