package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiHeader
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiHeader
import com.bselzer.ktx.openapi.model.reference.path.HeaderReferencePath

object ReferenceOfOpenApiHeaderSerializer : OpenApiReferenceOfSerializer<ReferenceOfOpenApiHeader, OpenApiHeader, HeaderReferencePath>(OpenApiHeaderSerializer)