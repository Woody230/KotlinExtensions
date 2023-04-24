package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.name.ClassName

class Serializable(
    serializerClassName: ClassName? = null
) : Annotation {
    override val className: ClassName = ClassName.SERIALIZABLE

    override val members: List<AnnotationMember> = buildList {
        serializerClassName?.let { with ->
            add(AnnotationMember("with", "$with::class"))
        }
    }
}