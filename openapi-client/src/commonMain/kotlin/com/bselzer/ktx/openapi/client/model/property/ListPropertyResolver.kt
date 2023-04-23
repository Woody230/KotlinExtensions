package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.declaration.InitializedPropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.name.ParameterizedTypeName
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

open class ListPropertyResolver(
    private val valueResolver: PropertyResolver
) : NestedPropertyResolver() {
    override fun canResolve(input: PropertyContext): Boolean = with(input) {
        val hasType = schema.types.contains(OpenApiSchemaType.ARRAY)
        val hasNestedSchema = schema.items != null
        return valueResolver.canResolve(input) && hasType && hasNestedSchema
    }

    // TODO maxItems, minItems, ... with setter
    override fun resolve(input: PropertyContext): Property {
        fun valueSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(input.schema.items) { "Expected a list to have an items schema." }
            return nestedProperty(nestedSchemaReference, input).type
        }

        return Property(
            type = ParameterizedTypeName(
                root = ClassName.LIST.copy(nullable = input.schema.isNullable),
                parameters = listOf(valueSchemaType())
            ),
            name = input.name,
            documentation = input.schema.description?.toDocumentation(),
            declaration = InitializedPropertyDeclaration(
                mutable = false,
                initializer = when {
                    input.schema.isNullable -> "null"
                    else -> "listOf()"
                }.toCodeBlock()
            )
        )
    }
}