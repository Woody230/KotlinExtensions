package com.bselzer.ktx.openapi.model.reference.path

class SchemaReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.SCHEMA, componentName)