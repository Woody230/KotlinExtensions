package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object IntSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.INT
    override val formats: Collection<String?> = setOf(null, "int32")
    override val type: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
}