package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.property.declaration.InitializedPropertyDeclaration
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.openapi.client.model.extensions.annotations
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

sealed class PrimitivePropertyResolver : PropertyResolver {
    protected abstract val types: Collection<OpenApiSchemaType>
    protected abstract val formats: Collection<String?>
    internal abstract val className: ClassName

    protected abstract fun instantiate(schema: OpenApiSchema): String

    override fun canResolve(context: PropertyContext): Boolean = with(context) {
        fun containsType() = types.any(schema.types::contains)
        fun containsFormat() = formats.contains(schema.format)
        return containsType() && containsFormat()
    }

    override fun resolve(context: PropertyContext): Property = with(context) {
        Property(
            type = className.copy(nullable = schema.isNullable),
            name = name,
            documentation = schema.description?.toDocumentation(),
            declaration = InitializedPropertyDeclaration(mutable = false, initializer = instantiate(schema).toCodeBlock()),
            annotations = schema.annotations()
        )
    }
}