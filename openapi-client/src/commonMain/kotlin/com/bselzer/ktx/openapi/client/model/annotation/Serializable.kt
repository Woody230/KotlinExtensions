package com.bselzer.ktx.openapi.client.model.annotation

import com.bselzer.ktx.openapi.client.type.name.ClassName

class Serializable(
    val serializerClassName: ClassName
) : Annotation {
    override val className: ClassName = ClassName.SERIALIZABLE

    override val members: List<AnnotationMember> = listOf(
        AnnotationMember("with", "$serializerClassName::class")
    )
}