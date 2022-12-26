package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.security.flow.*
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScope
import com.bselzer.ktx.serialization.context.getContentMapOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiOAuthFlowsSerializer : OpenApiObjectSerializer<OpenApiOAuthFlows>() {
    override fun JsonObject.deserialize(): OpenApiOAuthFlows = OpenApiOAuthFlows(
        implicit = getObjectOrNull("implicit") { it.toImplicitOAuthFlow() },
        password = getObjectOrNull("password") { it.toPasswordOAuthFlow() },
        clientCredentials = getObjectOrNull("clientCredentials") { it.toClientCredentialsOAuthFlow() },
        authorizationCode = getObjectOrNull("authorizationCode") { it.toAuthorizationCodeOAuthFlow() },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.toImplicitOAuthFlow(): ImplicitOAuthFlow = ImplicitOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        authorizationUrl = getUrl("authorizationUrl")
    )

    private fun JsonObject.toPasswordOAuthFlow(): PasswordOAuthFlow = PasswordOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        tokenUrl = getUrl("tokenUrl")
    )

    private fun JsonObject.toClientCredentialsOAuthFlow(): ClientCredentialsOAuthFlow = ClientCredentialsOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        tokenUrl = getUrl("tokenUrl")
    )

    private fun JsonObject.toAuthorizationCodeOAuthFlow(): AuthorizationCodeOAuthFlow = AuthorizationCodeOAuthFlow(
        refreshUrl = getUrlOrNull("refreshUrl"),
        scopes = getContentMapOrEmpty("scopes").mapKeys { entry -> OpenApiSecurityScope(entry.key) },
        extensions = getOpenApiExtensions(),
        authorizationUrl = getUrl("authorizationUrl"),
        tokenUrl = getUrl("tokenUrl")
    )
}