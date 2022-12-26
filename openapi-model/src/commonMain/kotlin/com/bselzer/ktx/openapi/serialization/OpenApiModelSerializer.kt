package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.*
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonObject

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

    protected fun JsonObject.getDescription(key: String): OpenApiDescription {
        val value = getContent(key)
        return OpenApiDescription(value)
    }

    protected fun JsonObject.getDescriptionOrNull(key: String): OpenApiDescription? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiDescription(value)
    }

    protected fun JsonObject.getExternalDocumentationOrNull(key: String): OpenApiExternalDocumentation? = getObjectOrNull(key) {
        OpenApiExternalDocumentationSerializer.deserialize(it)
    }

    protected fun JsonObject.getOpenApiExtensions(): OpenApiExtensions {
        val prefixed = filterKeys { key -> key.startsWith("x-") }
        return prefixed.mapValues { entry -> OpenApiValueSerializer.deserialize(entry.value) }
    }
}