package com.bselzer.ktx.openapi.model.reference.path

class CallbackReferencePath(
    documentPath: String?,
    componentName: String
) : OpenApiReferencePath(documentPath, ReferencePathComponent.CALLBACK, componentName)