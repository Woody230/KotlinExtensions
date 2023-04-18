package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.PropertyModifier
import com.bselzer.ktx.codegen.model.property.declaration.InitializedPropertyDeclaration
import com.bselzer.ktx.codegen.model.property.declaration.PropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.name.ParameterizedTypeName
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.openapi.client.internal.ExtensionConstants
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import com.bselzer.ktx.openapi.model.value.OpenApiMap
import com.bselzer.ktx.openapi.serialization.ReferenceOfOpenApiSchemaSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class MapPropertyResolver(
    resolvers: PropertyResolvers
) : NestedPropertyResolver(resolvers) {
    override fun canResolve(input: PropertyInput): Boolean = with(input) {
        val hasType = schema.types.contains(OpenApiSchemaType.OBJECT)
        val hasNestedSchema = schema.additionalProperties != null
        return hasType && hasNestedSchema
    }

    override fun resolve(input: PropertyInput): Property = with(input) {
        fun keySchemaType(): TypeName {
            // If the extension is not present, then the base assumption is that keys must be Strings.
            // If the extension is present, then assume that the key's value is a schema or a reference to a schema.
            val extension = schema.extensions[ExtensionConstants.KEY] ?: return ClassName.STRING
            require(extension is OpenApiMap) { "Expected the key to be in the form of an OpenApiMap." }

            // TODO model to be included in generation when it is a schema and not just a reference
            val serialized = Json.encodeToJsonElement(extension.value)
            val nestedSchemaReference = Json.decodeFromJsonElement(ReferenceOfOpenApiSchemaSerializer, serialized)
            return nestedSchemaType(nestedSchemaReference, input)
        }

        fun valueSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(schema.additionalProperties) { "Expected a map to have an additionalProperties schema." }
            return nestedSchemaType(nestedSchemaReference, input)
        }

        return object : Property {
            override val type: TypeName = ParameterizedTypeName(
                root = ClassName.MAP.copy(nullable = schema.isNullable),
                parameters = listOf(keySchemaType(), valueSchemaType())
            )
            override val name: String = input.name
            override val documentation: Documentation? = schema.description?.toDocumentation()
            override val annotations: Collection<Annotation> = emptyList()
            override val declaration: PropertyDeclaration = InitializedPropertyDeclaration(
                mutable = false,
                initializer = when {
                    schema.isNullable -> "null"
                    else -> "mapOf()"
                }.toCodeBlock()
            )

            override val receiver: TypeName? = null
            override val contextReceivers: Collection<TypeName> = emptyList()
            override val typeVariables: Collection<TypeVariableName> = emptyList()
            override val modifiers: Set<PropertyModifier> = emptySet()
        }
    }
}