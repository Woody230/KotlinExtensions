package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class StringSchemaHandler(
    private val default: String = "",
    private val nullDefault: String? = null
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.STRING
    override val formats: Collection<String?> = setOf(null)
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.let(::instantiate) ?: "null"
        else -> instantiate(default)
    }

    private fun instantiate(string: String) = "\"$string\""
}