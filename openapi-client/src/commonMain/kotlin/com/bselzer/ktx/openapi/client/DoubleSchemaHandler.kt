package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object DoubleSchemaHandler : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName.DOUBLE
    override val formats: Collection<String?> = setOf(null, "double")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.NUMBER)
}