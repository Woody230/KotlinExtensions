package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema
import com.bselzer.ktx.openapi.model.reference.path.SchemaReferencePath
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

object ReferenceOfOpenApiSchemaSerializer : OpenApiReferenceOfSerializer<ReferenceOfOpenApiSchema, OpenApiSchema, SchemaReferencePath>(OpenApiSchemaSerializer)