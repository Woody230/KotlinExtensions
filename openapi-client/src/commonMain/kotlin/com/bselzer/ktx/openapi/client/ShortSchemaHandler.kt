package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object ShortSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.SHORT
    override val formats: Collection<String?> = setOf("short")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
}