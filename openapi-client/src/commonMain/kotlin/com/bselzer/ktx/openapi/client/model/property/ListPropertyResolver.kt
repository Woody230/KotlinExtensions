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
    override fun canResolve(context: PropertyContext): Boolean = with(context) {
        val hasType = schema.types.contains(OpenApiSchemaType.ARRAY)
        val hasNestedSchema = schema.items != null
        return valueResolver.canResolve(context) && hasType && hasNestedSchema
    }

    // TODO maxItems, minItems, ... with setter
    override fun resolve(context: PropertyContext): Property = with(context) {
        fun valueSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(schema.items) { "Expected a list to have an items schema." }
            return nestedProperty(nestedSchemaReference, context).type
        }

        return Property(
            type = ParameterizedTypeName(
                root = ClassName.LIST.copy(nullable = schema.isNullable),
                parameters = listOf(valueSchemaType())
            ),
            name = name,
            documentation = schema.description?.toDocumentation(),
            declaration = InitializedPropertyDeclaration(
                mutable = false,
                initializer = when {
                    schema.isNullable -> "null"
                    else -> "listOf()"
                }.toCodeBlock()
            )
        )
    }
}