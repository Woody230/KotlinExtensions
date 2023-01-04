package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object LongSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.LONG
    override val formats: Collection<String?> = setOf("int64")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
}