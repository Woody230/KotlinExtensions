package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.path.OpenApiEncoding
import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaType
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiCallback
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiExample
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiHeader
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiLink
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiParameter
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiPathItem
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityRequirement
import com.bselzer.ktx.openapi.model.server.OpenApiServer
import kotlinx.serialization.json.JsonElement

internal typealias OpenApiParameterList = List<ReferenceOfOpenApiParameter>
internal typealias OpenApiExtensions = Map<String, JsonElement>
internal typealias OpenApiCallbacks = Map<String, ReferenceOfOpenApiCallback>
internal typealias OpenApiSecurityRequirements = List<OpenApiSecurityRequirement>
internal typealias OpenApiServers = List<OpenApiServer>
internal typealias OpenApiHeaders = Map<String, ReferenceOfOpenApiHeader>
internal typealias OpenApiContent = Map<OpenApiMediaTypeName, OpenApiMediaType>
internal typealias OpenApiLinks = Map<String, ReferenceOfOpenApiLink>
internal typealias OpenApiExamples = Map<String, ReferenceOfOpenApiExample>
internal typealias OpenApiEncodings = Map<OpenApiEncodingName, OpenApiEncoding>
internal typealias OpenApiPathItems = Map<String, ReferenceOfOpenApiPathItem>
internal typealias OpenApiTags = List<OpenApiTag>