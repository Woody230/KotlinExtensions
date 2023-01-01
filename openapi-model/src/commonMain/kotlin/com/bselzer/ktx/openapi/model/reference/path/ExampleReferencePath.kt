package com.bselzer.ktx.openapi.model.reference.path

class ExampleReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.EXAMPLE, componentName)