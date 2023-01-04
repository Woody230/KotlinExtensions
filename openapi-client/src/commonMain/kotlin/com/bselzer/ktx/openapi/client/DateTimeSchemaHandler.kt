package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import kotlinx.datetime.Instant

class DateTimeSchemaHandler(
    private val default: Instant = Instant.DISTANT_PAST,
    private val nullDefault: Instant? = null
) : PrimitiveSchemaHandler() {
    override val className: ClassName = ClassName("kotlinx.datetime", "Instant")
    override val formats: Collection<String?> = setOf("date-time")
    override val types: Collection<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING)
    override fun instantiate(schema: OpenApiSchema): String = when {
        schema.isNullable -> nullDefault?.let(::instantiate) ?: "null"
        else -> instantiate(default)
    }

    private fun instantiate(instant: Instant) = when (instant) {
        Instant.DISTANT_PAST -> "$className.DISTANT_PAST"
        Instant.DISTANT_FUTURE -> "$className.DISTANT_FUTURE"
        else -> "$className.parse(\"$instant\")"
    }
}