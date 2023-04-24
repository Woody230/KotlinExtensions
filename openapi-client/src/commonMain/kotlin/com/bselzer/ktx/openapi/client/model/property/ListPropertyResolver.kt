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
    override fun resolve(context: PropertyContext): Property {
        fun valueSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(context.schema.items) { "Expected a list to have an items schema." }
            return nestedProperty(nestedSchemaReference, context).type
        }

        return Property(
            type = ParameterizedTypeName(
                root = ClassName.LIST.copy(nullable = context.schema.isNullable),
                parameters = listOf(valueSchemaType())
            ),
            name = context.name,
            documentation = context.schema.description?.toDocumentation(),
            declaration = InitializedPropertyDeclaration(
                mutable = false,
                initializer = when {
                    context.schema.isNullable -> "null"
                    else -> "listOf()"
                }.toCodeBlock()
            ),
            annotations = extensionAnnotations(context)
        )
    }
}