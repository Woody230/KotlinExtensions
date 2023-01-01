package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExample
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.path.OpenApiCallback
import com.bselzer.ktx.openapi.model.path.OpenApiHeader
import com.bselzer.ktx.openapi.model.path.OpenApiPathItem
import com.bselzer.ktx.openapi.model.path.OpenApiRequestBody
import com.bselzer.ktx.openapi.model.reference.*
import com.bselzer.ktx.openapi.model.reference.path.*
import com.bselzer.ktx.openapi.model.response.OpenApiLink
import com.bselzer.ktx.openapi.model.response.OpenApiResponse
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScheme
import kotlinx.serialization.json.JsonObject

sealed class OpenApiReferenceOfSerializer<TReferenceOf, TValue, TReferencePath>(
    private val valueSerializer: OpenApiObjectSerializer<TValue>,
) : OpenApiObjectSerializer<TReferenceOf>() where TReferencePath : OpenApiReferencePath, TReferenceOf : OpenApiReferenceOf<TValue, TReferencePath> {

    override fun JsonObject.deserialize(): TReferenceOf {
        if (containsKey("\$ref")) {
            val reference = OpenApiReferenceSerializer<TReferencePath>().deserialize(this)
            return reference.toOpenApiReferenceOf() as TReferenceOf
        }

        val value = valueSerializer.deserialize(this)
        return value.toOpenApiReferenceOf() as TReferenceOf
    }

    private fun OpenApiReference<TReferencePath>.toOpenApiReferenceOf(): OpenApiReferenceOf<*, *> = when (`$ref`) {
        is CallbackReferencePath -> ReferenceOfOpenApiCallback(this as OpenApiReference<CallbackReferencePath>)
        is ExampleReferencePath -> ReferenceOfOpenApiExample(this as OpenApiReference<ExampleReferencePath>)
        is HeaderReferencePath -> ReferenceOfOpenApiHeader(this as OpenApiReference<HeaderReferencePath>)
        is LinkReferencePath -> ReferenceOfOpenApiLink(this as OpenApiReference<LinkReferencePath>)
        is ParameterReferencePath -> ReferenceOfOpenApiParameter(this as OpenApiReference<ParameterReferencePath>)
        is PathItemReferencePath -> ReferenceOfOpenApiPathItem(this as OpenApiReference<PathItemReferencePath>)
        is RequestBodyReferencePath -> ReferenceOfOpenApiRequestBody(this as OpenApiReference<RequestBodyReferencePath>)
        is ResponseReferencePath -> ReferenceOfOpenApiResponse(this as OpenApiReference<ResponseReferencePath>)
        is SchemaReferencePath -> ReferenceOfOpenApiSchema(this as OpenApiReference<SchemaReferencePath>)
        is SecuritySchemeReferencePath -> ReferenceOfOpenApiSecurityScheme(this as OpenApiReference<SecuritySchemeReferencePath>)
        else -> throw NotImplementedError("Unable to wrap the reference associated with an OpenApi model as a ReferenceOf.")
    }

    private fun TValue.toOpenApiReferenceOf(): OpenApiReferenceOf<*, *> = when (this) {
        is OpenApiCallback -> ReferenceOfOpenApiCallback(this)
        is OpenApiExample -> ReferenceOfOpenApiExample(this)
        is OpenApiHeader -> ReferenceOfOpenApiHeader(this)
        is OpenApiLink -> ReferenceOfOpenApiLink(this)
        is OpenApiParameter -> ReferenceOfOpenApiParameter(this)
        is OpenApiPathItem -> ReferenceOfOpenApiPathItem(this)
        is OpenApiRequestBody -> ReferenceOfOpenApiRequestBody(this)
        is OpenApiResponse -> ReferenceOfOpenApiResponse(this)
        is OpenApiSchema -> ReferenceOfOpenApiSchema(this)
        is OpenApiSecurityScheme -> ReferenceOfOpenApiSecurityScheme(this)
        else -> throw NotImplementedError("Unable to wrap the OpenApi model as a ReferenceOf.")
    }
}