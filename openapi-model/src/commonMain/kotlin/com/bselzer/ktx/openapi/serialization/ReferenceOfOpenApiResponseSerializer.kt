package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiResponse
import com.bselzer.ktx.openapi.model.reference.path.ResponseReferencePath
import com.bselzer.ktx.openapi.model.response.OpenApiResponse

object ReferenceOfOpenApiResponseSerializer: OpenApiReferenceOfSerializer<ReferenceOfOpenApiResponse, OpenApiResponse, ResponseReferencePath>(OpenApiResponseSerializer)