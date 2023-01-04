package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

sealed class PrimitiveSchemaHandler : SchemaHandler {
    abstract val types: Collection<OpenApiSchemaType>
    abstract val formats: Collection<String?>
    abstract val className: ClassName

    override fun canResolve(schema: OpenApiSchema): Boolean {
        fun containsType() = this.types.any(schema.types::contains)
        fun containsFormat() = this.formats.contains(schema.format)
        return containsType() && containsFormat()
    }

    override fun resolve(schema: OpenApiSchema): SchemaOutput = SchemaOutput(
        className = className,
        nullable = schema.types.contains(OpenApiSchemaType.NULL)
    )
}