package com.bselzer.ktx.openapi.model.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OpenApiSchemaType {
    @SerialName("string")
    STRING,

    @SerialName("number")
    NUMBER,

    @SerialName("integer")
    INTEGER,

    @SerialName("object")
    OBJECT,

    @SerialName("array")
    ARRAY,

    @SerialName("boolean")
    BOOLEAN,

    @SerialName("null")
    NULL
}