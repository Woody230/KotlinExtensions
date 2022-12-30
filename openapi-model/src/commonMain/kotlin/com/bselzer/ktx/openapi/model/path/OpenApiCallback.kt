package com.bselzer.ktx.openapi.model.path

import com.bselzer.ktx.openapi.model.OpenApiExtensible
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiPathItems
import com.bselzer.ktx.openapi.serialization.OpenApiCallbackSerializer

/**
 * A map of possible out-of band callbacks related to the parent operation. Each value in the map is a Path Item Object that describes a set of requests that may be initiated by the API provider and the expected responses. The key value used to identify the path item object is an expression, evaluated at runtime, that identifies a URL to use for the callback operation.
 *
 * To describe incoming requests from the API provider independent from another API call, use the webhooks field.
 */
@kotlinx.serialization.Serializable(OpenApiCallbackSerializer::class)
data class OpenApiCallback(
    /**
     * A Path Item Object, or a reference to one, used to define a callback request and expected responses. A [complete example](https://github.com/OAI/OpenAPI-Specification/blob/main/examples/v3.0/callback-example.yaml) is available.
     */
    val pathItems: OpenApiPathItems = emptyMap(),

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible