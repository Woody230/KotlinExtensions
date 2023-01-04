package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object DateTimeSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName("kotlinx.datetime", "Instant")
    override val formats: Collection<String?> = setOf("date-time")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING)
}