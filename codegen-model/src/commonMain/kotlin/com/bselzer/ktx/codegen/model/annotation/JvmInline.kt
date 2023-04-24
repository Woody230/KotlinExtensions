package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.name.ClassName

class JvmInline : Annotation {
    override val className: ClassName = ClassName.JVM_INLINE
    override val members: Collection<AnnotationMember> = emptyList()
}