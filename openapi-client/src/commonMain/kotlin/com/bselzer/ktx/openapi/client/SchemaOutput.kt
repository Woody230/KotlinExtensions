package com.bselzer.ktx.openapi.client

data class SchemaOutput(
    val className: ClassName? = null,
    val nullable: Boolean,
    val description: String? = null,
    val serializable: Serializable? = null,
    val instantiation: String
)