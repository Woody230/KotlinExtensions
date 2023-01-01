package com.bselzer.ktx.openapi.model.reference.path

class ParameterReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.PARAMETER, componentName)