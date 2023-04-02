package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

sealed class PrimitiveSchemaHandler : SchemaHandler {
    abstract val types: Collection<OpenApiSchemaType>
    abstract val formats: Collection<String?>
    abstract val className: ClassName

    protected abstract fun instantiate(schema: OpenApiSchema): String

    override fun canResolve(schema: OpenApiSchema): Boolean {
        fun containsType() = this.types.any(schema.types::contains)
        fun containsFormat() = this.formats.contains(schema.format)
        return containsType() && containsFormat()
    }

    override fun resolve(schema: OpenApiSchema): SchemaOutput = SchemaOutput(
        className = className,
        nullable = schema.types.contains(OpenApiSchemaType.NULL),
        instantiation = instantiate(schema)
    )
}