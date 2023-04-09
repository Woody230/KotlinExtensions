package com.bselzer.ktx.openapi.client.model.annotation

import com.bselzer.ktx.openapi.client.type.name.ClassName

interface Annotation {
    val className: ClassName
    val members: List<AnnotationMember>
}