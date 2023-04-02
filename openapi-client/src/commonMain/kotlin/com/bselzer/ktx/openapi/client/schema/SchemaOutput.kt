package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.client.type.ClassName
import com.bselzer.ktx.openapi.client.type.Serializable

data class SchemaOutput(
    val className: ClassName? = null,
    val nullable: Boolean,
    val mutable: Boolean = false,
    val description: String? = null,

    /**
     * The information about the serializer.
     */
    val serializable: Serializable? = null,

    val instantiation: String
)