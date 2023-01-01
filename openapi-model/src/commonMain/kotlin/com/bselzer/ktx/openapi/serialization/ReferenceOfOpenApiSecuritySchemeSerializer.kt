package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSecurityScheme
import com.bselzer.ktx.openapi.model.reference.path.SecuritySchemeReferencePath
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScheme

internal object ReferenceOfOpenApiSecuritySchemeSerializer :
    OpenApiReferenceOfSerializer<ReferenceOfOpenApiSecurityScheme, OpenApiSecurityScheme, SecuritySchemeReferencePath>(OpenApiSecuritySchemeSerializer)