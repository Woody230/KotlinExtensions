package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class ByteSchemaHandler(
    private val default: Byte = 0,
    private val nullDefault: Byte? = null,
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.BYTE
    override val formats: Collection<String?> = setOf("byte")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}