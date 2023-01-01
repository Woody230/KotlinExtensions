package com.bselzer.ktx.openapi.model.reference.path

class SecuritySchemeReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.SECURITY_SCHEME, componentName)