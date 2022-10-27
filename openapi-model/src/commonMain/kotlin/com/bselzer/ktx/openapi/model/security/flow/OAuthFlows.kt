package com.bselzer.ktx.openapi.model.security.flow

data class OAuthFlows(
    /**
     * Configuration for the OAuth Implicit flow
     */
    val implicit: ImplicitOAuthFlow? = null,

    /**
     * Configuration for the OAuth Resource Owner Password flow
     */
    val password: PasswordOAuthFlow? = null,

    /**
     * Configuration for the OAuth Client Credentials flow. Previously called application in OpenAPI 2.0.
     */
    val clientCredentials: ClientCredentialsOAuthFlow? = null,

    /**
     * Configuration for the OAuth Authorization Code flow. Previously called accessCode in OpenAPI 2.0.
     */
    val authorizationCode: AuthorizationCodeOAuthFlow? = null
)