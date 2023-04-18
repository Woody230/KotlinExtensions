package com.bselzer.ktx.openapi.client.model.property

class StringUuidPropertyResolver(
    default: String = "",
    nullDefault: String? = null
) : StringPropertyResolver(default, nullDefault) {
    override val formats: Collection<String?> = setOf("uuid")
}