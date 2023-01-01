package com.bselzer.ktx.openapi.model.information

import com.bselzer.ktx.openapi.model.OpenApiEmail
import com.bselzer.ktx.openapi.model.OpenApiExtensible
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiUrl

/**
 * Contact information for the exposed API.
 */
data class OpenApiContact(
    /**
     * The identifying name of the contact person/organization.
     */
    val name: String? = null,

    /**
     * The URL pointing to the contact information. This MUST be in the form of a URL.
     */
    val url: OpenApiUrl? = null,

    /**
     * The email address of the contact person/organization. This MUST be in the form of an email address.
     */
    val email: OpenApiEmail? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible