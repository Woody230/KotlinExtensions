package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object ByteSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.BYTE
    override val formats: Collection<String?> = setOf("byte")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER)
}