package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiCallback
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiCallback
import com.bselzer.ktx.openapi.model.reference.path.CallbackReferencePath

object ReferenceOfOpenApiCallbackSerializer :
    OpenApiReferenceOfSerializer<ReferenceOfOpenApiCallback, OpenApiCallback, CallbackReferencePath>(OpenApiCallbackSerializer)