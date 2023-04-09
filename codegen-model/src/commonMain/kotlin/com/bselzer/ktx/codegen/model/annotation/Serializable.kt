package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.ClassName

class Serializable(
    val serializerClassName: ClassName
) : Annotation {
    override val className: ClassName = ClassName.SERIALIZABLE

    override val members: List<AnnotationMember> = listOf(
        AnnotationMember("with", "$serializerClassName::class")
    )
}