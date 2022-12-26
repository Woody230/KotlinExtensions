package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.security.scheme.*
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getEnum
import com.bselzer.ktx.serialization.context.getObject
import kotlinx.serialization.json.JsonObject

object OpenApiSecuritySchemeSerializer : OpenApiObjectSerializer<OpenApiSecurityScheme>() {
    override fun JsonObject.deserialize(): OpenApiSecurityScheme {
        val type = getEnum<OpenApiSecuritySchemeType>("type")
        return when (type) {
            OpenApiSecuritySchemeType.API_KEY -> toApiKeySecurityScheme()
            OpenApiSecuritySchemeType.HTTP -> toHttpSecurityScheme()
            OpenApiSecuritySchemeType.MUTUAL_TLS -> toMutualTlsSecurityScheme()
            OpenApiSecuritySchemeType.OAUTH_2 -> toOAuth2SecurityScheme()
            OpenApiSecuritySchemeType.OPEN_ID_CONNECT -> toOpenIdConnectSecurityScheme()
        }
    }

    private fun JsonObject.toApiKeySecurityScheme() = ApiKeySecurityScheme(
        description = getDescriptionOrNull("description"),
        name = getContent("name").let(::OpenApiParameterName),
        `in` = getEnum("in")
    )

    private fun JsonObject.toHttpSecurityScheme() = HttpSecurityScheme(
        description = getDescriptionOrNull("description"),
        scheme = getContent("scheme"),
        bearerFormat = getContentOrNull("bearerFormat")
    )

    private fun JsonObject.toMutualTlsSecurityScheme() = MutualTlsSecurityScheme(
        description = getDescriptionOrNull("description")
    )

    private fun JsonObject.toOAuth2SecurityScheme() = OAuth2SecurityScheme(
        description = getDescriptionOrNull("description"),
        flows = getObject("flows", OpenApiOAuthFlowsSerializer::deserialize)
    )

    private fun JsonObject.toOpenIdConnectSecurityScheme() = OpenIdConnectSecurityScheme(
        description = getDescriptionOrNull("description"),
        openIdConnectUrl = getUrl("openIdConnectUrl")
    )
}