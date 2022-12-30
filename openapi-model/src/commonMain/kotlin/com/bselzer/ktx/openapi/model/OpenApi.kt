package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.information.OpenApiInformation
import com.bselzer.ktx.openapi.model.path.OpenApiPaths
import com.bselzer.ktx.openapi.serialization.OpenApiSerializer

/**
 * This is the root object of the OpenAPI document.
 */
@kotlinx.serialization.Serializable(OpenApiSerializer::class)
data class OpenApi(
    /**
     * REQUIRED. This string MUST be the version number of the OpenAPI Specification that the OpenAPI document uses. The openapi field SHOULD be used by tooling to interpret the OpenAPI document. This is not related to the API info.version string.
     */
    val openapi: String = "3.1.0",

    /**
     * REQUIRED. Provides metadata about the API. The metadata MAY be used by tooling as required.
     */
    val info: OpenApiInformation,

    /**
     * The default value for the $schema keyword within Schema Objects contained within this OAS document. This MUST be in the form of a URI.
     */
    val jsonSchemaDialect: String = "https://spec.openapis.org/oas/3.1/dialect/base",

    /**
     * An array of Server Objects, which provide connectivity information to a target server. If the servers property is not provided, or is an empty array, the default value would be a Server Object with a url value of /.
     */
    val servers: OpenApiServers = emptyList(),

    /**
     * The available paths and operations for the API.
     */
    val paths: OpenApiPaths? = null,

    /**
     * The incoming webhooks that MAY be received as part of this API and that the API consumer MAY choose to implement. Closely related to the callbacks feature, this section describes requests initiated other than by an API call, for example by an out of band registration. The key name is a unique string to refer to each webhook, while the (optionally referenced) Path Item Object describes a request that may be initiated by the API provider and the expected responses. An example is available.
     */
    val webhooks: OpenApiPathItems = emptyMap(),

    /**
     * An element to hold various schemas for the document.
     */
    val components: OpenApiComponents? = null,

    /**
     * A declaration of which security mechanisms can be used across the API. The list of values includes alternative security requirement objects that can be used. Only one of the security requirement objects need to be satisfied to authorize a request. Individual operations can override this definition. To make security optional, an empty security requirement ({}) can be included in the array.
     */
    val security: OpenApiSecurityRequirements = emptyList(),

    /**
     * A list of tags used by the document with additional metadata. The order of the tags can be used to reflect on their order by the parsing tools. Not all tags that are used by the Operation Object must be declared. The tags that are not declared MAY be organized randomly or based on the tools’ logic. Each tag name in the list MUST be unique.
     */
    val tags: OpenApiTags = emptyList(),

    /**
     * Additional external documentation.
     */
    val externalDocs: OpenApiExternalDocumentation? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible