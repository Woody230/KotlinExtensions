package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityRequirement

typealias OpenApiParameterList = List<OpenApiReferenceOf<OpenApiParameter>>
typealias OpenApiExtensions = Map<String, OpenApiExtension>
typealias OpenApiCallbacks = Map<String, OpenApiReferenceOf<OpenApiCallback>>
typealias OpenApiSecurityRequirements = List<OpenApiSecurityRequirement>
typealias OpenApiServers = List<OpenApiServer>
typealias OpenApiHeaders = Map<String, OpenApiReferenceOf<OpenApiHeader>>
typealias OpenApiContent = Map<OpenApiMediaTypeName, OpenApiMediaType>
typealias OpenApiLinks = Map<String, OpenApiReferenceOf<OpenApiLink>>
typealias OpenApiExamples = Map<String, OpenApiReferenceOf<OpenApiExample>>
typealias OpenApiEncodings = Map<OpenApiEncodingName, OpenApiEncoding>
typealias OpenApiSchemaReference = OpenApiReferenceOf<OpenApiSchema>
typealias OpenApiPathItems = Map<String, OpenApiReferenceOf<OpenApiPathItem>>