package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.annotation.Serializable
import com.bselzer.ktx.codegen.model.type.TypeName

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