package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.path.*
import com.bselzer.ktx.openapi.model.response.OpenApiLink
import com.bselzer.ktx.openapi.model.schema.OpenApiExtension
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityRequirement
import com.bselzer.ktx.openapi.model.server.OpenApiServer

internal typealias OpenApiParameterList = List<OpenApiReferenceOf<OpenApiParameter>>
internal typealias OpenApiExtensions = Map<String, OpenApiExtension>
internal typealias OpenApiCallbacks = Map<String, OpenApiReferenceOf<OpenApiCallback>>
internal typealias OpenApiSecurityRequirements = List<OpenApiSecurityRequirement>
internal typealias OpenApiServers = List<OpenApiServer>
internal typealias OpenApiHeaders = Map<String, OpenApiReferenceOf<OpenApiHeader>>
internal typealias OpenApiContent = Map<OpenApiMediaTypeName, OpenApiMediaType>
internal typealias OpenApiLinks = Map<String, OpenApiReferenceOf<OpenApiLink>>
internal typealias OpenApiExamples = Map<String, OpenApiReferenceOf<OpenApiExample>>
internal typealias OpenApiEncodings = Map<OpenApiEncodingName, OpenApiEncoding>
internal typealias OpenApiSchemaReference = OpenApiReferenceOf<OpenApiSchema>
internal typealias OpenApiPathItems = Map<String, OpenApiReferenceOf<OpenApiPathItem>>
internal typealias OpenApiTags = List<OpenApiTag>