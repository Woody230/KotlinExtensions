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
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class ListPropertyResolver(
    resolvers: PropertyResolvers
) : NestedPropertyResolver(resolvers) {
    override fun canResolve(input: PropertyInput): Boolean = with(input) {
        val hasType = schema.types.contains(OpenApiSchemaType.ARRAY)
        val hasNestedSchema = schema.items != null
        return hasType && hasNestedSchema
    }

    // TODO maxItems, minItems, ... with setter
    override fun resolve(input: PropertyInput): Property {
        fun nestedSchemaType(): TypeName {
            val nestedSchemaReference = requireNotNull(input.schema.items) { "Expected a list to have an items schema." }
            return nestedSchemaType(nestedSchemaReference, input)
        }

        return object : Property {
            override val type: TypeName = ParameterizedTypeName(
                root = ClassName.LIST.copy(nullable = input.schema.isNullable),
                parameters = listOf(nestedSchemaType())
            )

            override val name: String = input.name
            override val documentation: Documentation? = input.schema.description?.toDocumentation()
            override val annotations: Collection<Annotation> = emptyList()

            override val declaration: PropertyDeclaration = InitializedPropertyDeclaration(
                mutable = false,
                initializer = when {
                    input.schema.isNullable -> "null"
                    else -> "listOf()"
                }.toCodeBlock()

            )
            override val receiver: TypeName? = null
            override val contextReceivers: Collection<TypeName> = emptyList()
            override val typeVariables: Collection<TypeVariableName> = emptyList()
            override val modifiers: Set<PropertyModifier> = emptySet()
        }
    }
}