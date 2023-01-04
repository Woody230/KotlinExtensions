package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiRequestBody
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiRequestBody
import com.bselzer.ktx.openapi.model.reference.path.RequestBodyReferencePath

internal object ReferenceOfOpenApiRequestBodySerializer :
    OpenApiReferenceOfSerializer<ReferenceOfOpenApiRequestBody, OpenApiRequestBody, RequestBodyReferencePath>(OpenApiRequestBodySerializer)