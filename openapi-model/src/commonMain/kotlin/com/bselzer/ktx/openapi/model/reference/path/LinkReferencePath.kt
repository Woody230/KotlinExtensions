package com.bselzer.ktx.openapi.model.reference.path

class LinkReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.LINK, componentName)