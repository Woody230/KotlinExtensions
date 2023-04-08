package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

sealed class PrimitivePropertyHandler : PropertyHandler {
    protected abstract val types: Collection<OpenApiSchemaType>
    protected abstract val formats: Collection<String?>
    internal abstract val className: ClassName

    protected abstract fun instantiate(schema: OpenApiSchema): String

    override fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean {
        fun containsType() = this.types.any(schema.types::contains)
        fun containsFormat() = this.formats.contains(schema.format)
        return containsType() && containsFormat()
    }

    override fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): PropertyOutput = PropertyOutput(
        typeName = className,
        nullable = schema.isNullable,
        description = schema.description?.toString(),
        instantiation = instantiate(schema)
    )
}