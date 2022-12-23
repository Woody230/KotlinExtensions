package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.information.OpenApiContact
import com.bselzer.ktx.openapi.model.information.OpenApiInformation
import com.bselzer.ktx.openapi.model.information.OpenApiLicense
import com.bselzer.ktx.openapi.model.schema.OpenApiExtension
import com.bselzer.ktx.openapi.model.value.*
import com.bselzer.ktx.serialization.context.getString
import com.bselzer.ktx.serialization.context.getStringOrNull
import com.bselzer.ktx.serialization.context.toJsonObjectOrNull
import kotlinx.serialization.json.*

object OpenApiContext {
    private fun JsonObject.getOpenApiContactOrNull(key: String): OpenApiContact? = get(key)?.toJsonObjectOrNull()?.toOpenApiContact()
    private fun JsonObject.getOpenApiLicenseOrNull(key: String): OpenApiLicense? = get(key)?.toJsonObjectOrNull()?.toOpenApiLicense()

    fun JsonObject.toOpenApi(): OpenApi = OpenApi(
        openapi = getStringOrNull("openapi") ?: "3.1.0",
        info = jsonObject.toOpenApiInformation()
    )

    fun JsonObject.toOpenApiInformation(): OpenApiInformation = OpenApiInformation(
        title = getString("title"),
        summary = getStringOrNull("summary"),
        description = getDescriptionOrNull("description"),
        termsOfService = getStringOrNull("termsOfService"),
        contact = getOpenApiContactOrNull("contact"),
        license = getOpenApiLicenseOrNull("license"),
        version = getString("version"),
        extensions = getOpenApiExtensions()
    )


    fun JsonObject.toOpenApiContact(): OpenApiContact = OpenApiContact(
        name = getStringOrNull("name"),
        url = getUrlOrNull("url"),
        email = getEmailOrNull("email"),
        extensions = getOpenApiExtensions()
    )

    fun JsonObject.toOpenApiLicense(): OpenApiLicense = OpenApiLicense(
        name = getString("name"),
        identifier = getStringOrNull("identifier"),
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
        val value = getStringOrNull(key) ?: return null
        return OpenApiEmail(value)
    }

    private fun JsonObject.getUrlOrNull(key: String): OpenApiUrl? {
        val value = getStringOrNull(key) ?: return null
        return OpenApiUrl(value)
    }

    private fun JsonObject.getDescriptionOrNull(key: String): OpenApiDescription? {
        val value = getStringOrNull(key) ?: return null
        return OpenApiDescription(value)
    }
}