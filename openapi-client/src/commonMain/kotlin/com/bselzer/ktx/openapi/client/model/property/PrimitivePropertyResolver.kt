package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.PropertyModifier
import com.bselzer.ktx.codegen.model.property.declaration.InitializedPropertyDeclaration
import com.bselzer.ktx.codegen.model.property.declaration.PropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.name.TypeName
import com.bselzer.ktx.codegen.model.type.name.TypeVariableName
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

sealed class PrimitivePropertyResolver : PropertyResolver {
    protected abstract val types: Collection<OpenApiSchemaType>
    protected abstract val formats: Collection<String?>
    internal abstract val className: ClassName

    protected abstract fun instantiate(schema: OpenApiSchema): String

    override fun canResolve(input: PropertyInput): Boolean = with(input) {
        fun containsType() = types.any(schema.types::contains)
        fun containsFormat() = formats.contains(schema.format)
        return containsType() && containsFormat()
    }

    override fun resolve(input: PropertyInput): Property = with(input) {
        object : Property {
            override val type: TypeName = className.copy(nullable = schema.isNullable)
            override val documentation: Documentation? = schema.description?.toDocumentation()
            override val declaration: PropertyDeclaration = InitializedPropertyDeclaration(mutable = false, initializer = instantiate(schema).toCodeBlock())
            override val annotations: Collection<Annotation> = emptyList()
            override val contextReceivers: Collection<TypeName> = emptyList()
            override val modifiers: Set<PropertyModifier> = emptySet()
            override val name: String = input.name
            override val receiver: TypeName? = null
            override val typeVariables: Collection<TypeVariableName> = emptyList()
        }
    }
}