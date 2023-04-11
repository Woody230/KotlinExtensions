package com.bselzer.ktx.codegen.model.annotation

import com.bselzer.ktx.codegen.model.type.ClassName

interface Annotation {
    val siteTarget: AnnotationSiteTarget?
    val className: ClassName
    val members: List<AnnotationMember>
}