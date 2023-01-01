package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiParameter
import com.bselzer.ktx.openapi.model.reference.path.ParameterReferencePath

object ReferenceOfOpenApiParameterSerializer : OpenApiReferenceOfSerializer<ReferenceOfOpenApiParameter, OpenApiParameter, ParameterReferencePath>(OpenApiParameterSerializer)