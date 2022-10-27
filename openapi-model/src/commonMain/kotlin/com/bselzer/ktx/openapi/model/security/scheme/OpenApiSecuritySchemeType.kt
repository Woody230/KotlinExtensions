package com.bselzer.ktx.openapi.model.security.scheme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OpenApiSecuritySchemeType {
    @SerialName("apiKey")
    API_KEY,

    @SerialName("http")
    HTTP,

    @SerialName("mutualTLS")
    MUTUAL_TLS,

    @SerialName("oauth2")
    OAUTH_2,

    @SerialName("openIdConnect")
    OPEN_ID_CONNECT
}