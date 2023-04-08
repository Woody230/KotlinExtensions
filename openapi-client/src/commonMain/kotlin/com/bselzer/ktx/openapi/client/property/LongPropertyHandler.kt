package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.client.type.name.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

class LongPropertyHandler(
    private val default: Long = 0,
    private val nullDefault: Long? = null
) : PrimitivePropertyHandler() {
    override val className: ClassName = ClassName.LONG
    override val formats: Collection<String?> = setOf("int64")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.toString() ?: "null"
        else -> default.toString()
    }
}