package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object StringSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.STRING
    override val formats: Collection<String?> = setOf(null)
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING)
}