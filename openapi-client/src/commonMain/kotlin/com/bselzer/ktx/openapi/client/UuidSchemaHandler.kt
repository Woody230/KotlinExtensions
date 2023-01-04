package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object UuidSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.STRING
    override val formats: Collection<String?> = setOf("uuid")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING)
}