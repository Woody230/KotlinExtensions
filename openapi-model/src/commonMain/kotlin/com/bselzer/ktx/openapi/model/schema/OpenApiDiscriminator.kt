package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier
import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * When request bodies or response payloads may be one of a number of different schemas, a discriminator object can be used to aid in serialization, deserialization, and validation. The discriminator is a specific object in a schema which is used to inform the consumer of the document of an alternative schema based on the value associated with it.
 *
 * When using the discriminator, inline schemas will not be considered.
 */
data class OpenApiDiscriminator(
    /**
     * REQUIRED. The name of the property in the payload that will hold the discriminator value.
     */
    val propertyName: String,

    /**
     * An object to hold mappings between payload values and schema names or references.
     */
    val mapping: Map<String, OpenApiReferenceIdentifier> = emptyMap(),

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible