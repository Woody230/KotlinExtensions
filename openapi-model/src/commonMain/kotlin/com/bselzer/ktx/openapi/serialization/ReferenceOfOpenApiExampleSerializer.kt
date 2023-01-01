package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExample
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiExample
import com.bselzer.ktx.openapi.model.reference.path.ExampleReferencePath

internal object ReferenceOfOpenApiExampleSerializer : OpenApiReferenceOfSerializer<ReferenceOfOpenApiExample, OpenApiExample, ExampleReferencePath>(OpenApiExampleSerializer)