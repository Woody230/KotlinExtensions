package com.bselzer.ktx.openapi.model.reference.path

class PathItemReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.PATH_ITEM, componentName)