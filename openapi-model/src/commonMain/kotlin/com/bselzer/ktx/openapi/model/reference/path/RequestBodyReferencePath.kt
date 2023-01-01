package com.bselzer.ktx.openapi.model.reference.path

class RequestBodyReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.REQUEST_BODY, componentName)