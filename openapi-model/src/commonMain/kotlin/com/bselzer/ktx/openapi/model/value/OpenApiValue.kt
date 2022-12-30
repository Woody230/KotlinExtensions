package com.bselzer.ktx.openapi.model.value

import com.bselzer.ktx.openapi.serialization.OpenApiValueSerializer

@kotlinx.serialization.Serializable(OpenApiValueSerializer::class)
sealed interface OpenApiValue