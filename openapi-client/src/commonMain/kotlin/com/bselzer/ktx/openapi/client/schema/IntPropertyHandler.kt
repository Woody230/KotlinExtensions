package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class IntPropertyHandler(
    private val default: Int = 0,
    private val nullDefault: Int? = null
) : PrimitivePropertyHandler() {
    override val className: ClassName = ClassName.INT
    override val formats: Collection<String?> = setOf(null, "int32")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}