package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiPathItem
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiPathItem
import com.bselzer.ktx.openapi.model.reference.path.PathItemReferencePath

internal object ReferenceOfOpenApiPathItemSerializer :
    OpenApiReferenceOfSerializer<ReferenceOfOpenApiPathItem, OpenApiPathItem, PathItemReferencePath>(OpenApiPathItemSerializer)