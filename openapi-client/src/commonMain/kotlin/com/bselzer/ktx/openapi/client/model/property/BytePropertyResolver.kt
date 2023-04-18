package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

open class BytePropertyResolver(
    private val default: Byte = 0,
    private val nullDefault: Byte? = null,
) : PrimitivePropertyResolver() {
    override val className: ClassName = ClassName.BYTE
    override val formats: Collection<String?> = setOf("byte")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}