package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.client.type.Serializable
import com.bselzer.ktx.openapi.client.type.TypeName

data class PropertyOutput(
    val typeName: TypeName,
    val nullable: Boolean,
    val description: String?,

    /**
     * The information about the serializer.
     */
    val serializable: Serializable? = null,

    val instantiation: String
)