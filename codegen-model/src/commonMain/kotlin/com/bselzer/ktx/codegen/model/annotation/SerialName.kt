package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.name.ClassName

class SerialName(
    name: String
) : Annotation {
    override val siteTarget: AnnotationSiteTarget? = null
    override val className: ClassName = ClassName.SERIAL_NAME
    override val members: Collection<AnnotationMember> = listOf(
        AnnotationMember("value", name)
    )
}