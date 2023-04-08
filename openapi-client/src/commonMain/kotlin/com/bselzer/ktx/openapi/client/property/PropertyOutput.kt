package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.client.type.name.Serializable
import com.bselzer.ktx.openapi.client.type.name.TypeName

class PropertyOutput(
    val typeName: TypeName,
    val nullable: Boolean,
    val description: String?,

    /**
     * The information about the serializer.
     */
    val serializable: Serializable? = null,

    val instantiation: String
)