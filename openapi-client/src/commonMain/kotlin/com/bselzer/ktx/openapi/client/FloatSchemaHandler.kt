package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object FloatSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.FLOAT
    override val formats: Collection<String?> = setOf("float")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.NUMBER)
}