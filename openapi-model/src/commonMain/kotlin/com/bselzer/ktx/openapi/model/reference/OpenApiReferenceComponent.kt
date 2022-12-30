package com.bselzer.ktx.openapi.model.reference

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
enum class OpenApiReferenceComponent {
    @SerialName("schemas")
    SCHEMA,

    @SerialName("responses")
    RESPONSE,

    @SerialName("parameters")
    PARAMETER,

    @SerialName("examples")
    EXAMPLE,

    @SerialName("requestBodies")
    REQUEST_BODY,

    @SerialName("headers")
    HEADER,

    @SerialName("securitySchemes")
    SECURITY_SCHEME,

    @SerialName("link")
    LINK,

    @SerialName("callbacks")
    CALLBACK,

    @SerialName("pathItems")
    PATH_ITEM
}