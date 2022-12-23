package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.information.OpenApiContact
import com.bselzer.ktx.openapi.model.information.OpenApiInformation
import com.bselzer.ktx.openapi.model.information.OpenApiLicense
import com.bselzer.ktx.openapi.model.schema.OpenApiExtension
import com.bselzer.ktx.openapi.model.server.OpenApiServer
import com.bselzer.ktx.openapi.model.server.OpenApiServerVariable
import com.bselzer.ktx.openapi.model.value.*
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentListOrEmpty
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.*

object OpenApiContext {
    private fun JsonObject.getOpenApiContactOrNull(key: String): OpenApiContact? = get(key)?.jsonObject?.toOpenApiContact()
    private fun JsonObject.getOpenApiLicenseOrNull(key: String): OpenApiLicense? = get(key)?.jsonObject?.toOpenApiLicense()
    private fun JsonObject.getOpenApiServerVariablesOrEmpty(key: String): Map<String, OpenApiServerVariable> = get(key)?.jsonObject

    fun JsonObject.toOpenApi(): OpenApi = OpenApi(
        openapi = getContentOrNull("openapi") ?: "3.1.0",
        info = jsonObject.toOpenApiInformation(),
        jsonSchemaDialect = getContentOrNull("jsonSchemaDialect") ?: "https://spec.openapis.org/oas/3.1/dialect/base",
        servers =
    )

    fun JsonObject.toOpenApiInformation(): OpenApiInformation = OpenApiInformation(
        title = getContent("title"),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        termsOfService = getContentOrNull("termsOfService"),
        contact = getOpenApiContactOrNull("contact"),
        license = getOpenApiLicenseOrNull("license"),
        version = getContent("version"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiServer(): OpenApiServer = OpenApiServer(
        url = getUrl("url"),
        description = getDescriptionOrNull("description"),
        variables =
    )

    fun JsonObject.toOpenApiServerVariable(): OpenApiServerVariable = OpenApiServerVariable(
        enum = getContentListOrEmpty("enum"),
        default = getContent("default"),
        description = getDescriptionOrNull("description"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiContact(): OpenApiContact = OpenApiContact(
        name = getContentOrNull("name"),
        url = getUrlOrNull("url"),
        email = getEmailOrNull("email"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiLicense(): OpenApiLicense = OpenApiLicense(
        name = getContent("name"),
        identifier = getContentOrNull("identifier"),
        url = getUrlOrNull("url"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.getOpenApiExtensions(): OpenApiExtensions {
        val prefixed = filterKeys { key -> key.startsWith("x-") }
        return prefixed.mapValues { entry -> entry.value.toOpenApiValue().toOpenApiExtension() }
    }

    private fun JsonElement.toOpenApiValue(): OpenApiValue = when (this) {
        is JsonNull -> OpenApiNull
        is JsonPrimitive -> toOpenApiValue()
        is JsonObject -> toOpenApiValue()
        is JsonArray -> toOpenApiValue()
    }

    private fun JsonArray.toOpenApiValue(): OpenApiValue {
        val values = map { value -> value.toOpenApiValue() }
        return OpenApiList(values)
    }

    private fun JsonObject.toOpenApiValue(): OpenApiValue {
        val entries = mapValues { entry -> entry.value.toOpenApiValue() }
        return OpenApiMap(entries)
    }

    private fun JsonPrimitive.toOpenApiValue(): OpenApiValue = when {
        this is JsonNull -> OpenApiNull
        isString -> OpenApiString(content) // Need to check isString before the rest to verify we don't have a quoted literal
        intOrNull != null -> OpenApiNumber(int)
        longOrNull != null -> OpenApiNumber(long)
        floatOrNull != null -> OpenApiNumber(float)
        doubleOrNull != null -> OpenApiNumber(double)
        booleanOrNull != null -> OpenApiBoolean(boolean)
        else -> OpenApiString(content)
    }

    private fun OpenApiValue.toOpenApiExtension() = OpenApiExtension(this)

    private fun JsonObject.getEmailOrNull(key: String): OpenApiEmail? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiEmail(value)
    }

    private fun JsonObject.getUrl(key: String): OpenApiUrl {
        val value = getContent(key)
        return OpenApiUrl(value)
    }

    private fun JsonObject.getUrlOrNull(key: String): OpenApiUrl? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiUrl(value)
    }

    private fun JsonObject.getDescriptionOrNull(key: String): OpenApiDescription? {
        val value = getContentOrNull(key) ?: return null
        return OpenApiDescription(value)
    }
}