package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.*
import com.bselzer.ktx.openapi.model.schema.OpenApiExtension
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityRequirement
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecuritySchemeName
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScope
import com.bselzer.ktx.openapi.model.value.OpenApiValue
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray

sealed class OpenApiModelSerializer<T> : KSerializer<T> {
    protected fun JsonObject.getEmailOrNull(key: String): OpenApiEmail? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiEmail(value)
    }

    protected fun JsonObject.getUrl(key: String): OpenApiUrl {
        val value = getContent(key)
        return OpenApiUrl(value)
    }

    protected fun JsonObject.getUrlOrNull(key: String): OpenApiUrl? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiUrl(value)
    }

    protected fun JsonObject.getDescriptionOrNull(key: String): OpenApiDescription? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiDescription(value)
    }

    protected fun JsonObject.getExternalDocumentationOrNull(key: String): OpenApiExternalDocumentation? = getObjectOrNull(key) {
        OpenApiExternalDocumentationSerializer().deserialize(it)
    }

    protected fun JsonObject.getOpenApiExtensions(): OpenApiExtensions {
        val prefixed = filterKeys { key -> key.startsWith("x-") }
        return prefixed.mapValues { entry ->
            val value = OpenApiValueSerializer().deserialize(entry.value)
            OpenApiExtension(value)
        }
    }

    protected fun JsonElement.toOpenApiValue(): OpenApiValue = OpenApiValueSerializer().deserialize(this)
    protected fun JsonObject.toOpenApiSchema(): OpenApiSchema = OpenApiSchemaSerializer().deserialize(this)
    protected fun JsonObject.toOpenApiSchemaReference(): OpenApiSchemaReference {
        val valueSerializer = OpenApiSchemaSerializer()
        return OpenApiReferenceOfSerializer(valueSerializer).deserialize(this)
    }

    protected fun JsonObject.getSecurityRequirements(key: String): OpenApiSecurityRequirements = getObjectListOrEmpty(key) {
        val names = mapKeys { entry -> OpenApiSecuritySchemeName(entry.key) }.mapValues { entry ->
            entry.value.jsonArray.map { element ->
                OpenApiSecurityScope(element.toContent())
            }
        }

        OpenApiSecurityRequirement(names)
    }
}