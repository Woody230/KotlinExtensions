package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.name.ClassName

class Serializable(
    serializerClassName: ClassName
) : Annotation {
    override val className: ClassName = ClassName.SERIALIZABLE

    override val members: List<AnnotationMember> = listOf(
        AnnotationMember("with", "$serializerClassName::class")
    )
}