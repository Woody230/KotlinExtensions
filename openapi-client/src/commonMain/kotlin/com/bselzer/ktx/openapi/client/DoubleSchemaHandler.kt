package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class DoubleSchemaHandler(
    private val default: Double = 0.0,
    private val nullDefault: Double? = null,
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.DOUBLE
    override val formats: Collection<String?> = setOf(null, "double")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.NUMBER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}