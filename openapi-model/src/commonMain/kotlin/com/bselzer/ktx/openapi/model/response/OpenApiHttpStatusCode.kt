package com.bselzer.ktx.openapi.model.response

import kotlin.jvm.JvmInline

/**
 * This field MUST be enclosed in quotation marks (for example, “200”) for compatibility between JSON and YAML. To define a range of response codes, this field MAY contain the uppercase wildcard character X. For example, 2XX represents all response codes between [200-299]. Only the following range definitions are allowed: 1XX, 2XX, 3XX, 4XX, and 5XX. If a response is defined using an explicit code, the explicit code definition takes precedence over the range definition for that code.
 */
@JvmInline
value class OpenApiHttpStatusCode(private val value: String) {
    override fun toString(): String = value
}