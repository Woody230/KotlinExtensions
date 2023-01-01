package com.bselzer.ktx.openapi.model.reference.path

class HeaderReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.HEADER, componentName)