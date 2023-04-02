package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class FloatSchemaHandler(
    private val default: Float = 0f,
    private val nullDefault: Float? = null
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.FLOAT
    override val formats: Collection<String?> = setOf("float")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.NUMBER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}