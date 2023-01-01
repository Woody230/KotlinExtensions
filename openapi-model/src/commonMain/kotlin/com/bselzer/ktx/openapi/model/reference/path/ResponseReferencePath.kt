package com.bselzer.ktx.openapi.model.reference.path

class ResponseReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.RESPONSE, componentName)