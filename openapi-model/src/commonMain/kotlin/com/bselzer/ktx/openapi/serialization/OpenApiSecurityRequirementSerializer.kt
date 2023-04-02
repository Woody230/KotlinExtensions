package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityRequirement
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecuritySchemeName
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScope
import com.bselzer.ktx.serialization.context.toContentList
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray

object OpenApiSecurityRequirementSerializer : OpenApiObjectSerializer<OpenApiSecurityRequirement>() {
    override fun JsonObject.deserialize(): OpenApiSecurityRequirement {
        val withSchemeName = mapKeys { entry -> OpenApiSecuritySchemeName(entry.key) }
        val withScopes = withSchemeName.mapValues { entry ->
            entry.value.jsonArray.toContentList().map(::OpenApiSecurityScope)
        }

        return OpenApiSecurityRequirement(withScopes)
    }
}