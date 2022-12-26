package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.security.scheme.*
import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getObject
import kotlinx.serialization.json.JsonObject

class OpenApiSecuritySchemeSerializer : OpenApiObjectSerializer<OpenApiSecurityScheme>() {
    override fun JsonObject.deserialize(): OpenApiSecurityScheme {
        val type = getContent("type").decode<OpenApiSecuritySchemeType>()
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
        name = OpenApiParameterName(getContent("name")),
        `in` = getContent("in").decode()
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
        flows = getObject("flows") { OpenApiOAuthFlowsSerializer().deserialize(it) }
    )

    private fun JsonObject.toOpenIdConnectSecurityScheme() = OpenIdConnectSecurityScheme(
        description = getDescriptionOrNull("description"),
        openIdConnectUrl = getUrl("openIdConnectUrl")
    )
}