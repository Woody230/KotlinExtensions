package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.name.ClassName

data class SimpleAnnotation(
    override val className: ClassName,
    override val members: Collection<AnnotationMember> = emptyList(),
    override val siteTarget: AnnotationSiteTarget? = null,
) : Annotation