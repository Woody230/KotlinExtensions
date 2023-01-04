package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class IntSchemaHandler(
    private val default: Int = 0,
    private val nullDefault: Int? = null
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.INT
    override val formats: Collection<String?> = setOf(null, "int32")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}