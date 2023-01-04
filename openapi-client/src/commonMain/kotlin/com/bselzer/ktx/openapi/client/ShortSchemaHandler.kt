package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class ShortSchemaHandler(
    private val default: Short = 0,
    private val nullDefault: Short? = null
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.SHORT
    override val formats: Collection<String?> = setOf("short")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}