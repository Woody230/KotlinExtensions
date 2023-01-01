package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiLink
import com.bselzer.ktx.openapi.model.reference.path.LinkReferencePath
import com.bselzer.ktx.openapi.model.response.OpenApiLink

object ReferenceOfOpenApiLinkSerializer: OpenApiReferenceOfSerializer<ReferenceOfOpenApiLink, OpenApiLink, LinkReferencePath>(OpenApiLinkSerializer)