package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class BytePropertyHandler(
    private val default: Byte = 0,
    private val nullDefault: Byte? = null,
) : PrimitivePropertyHandler() {
    override val className: ClassName = ClassName.BYTE
    override val formats: Collection<String?> = setOf("byte")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}