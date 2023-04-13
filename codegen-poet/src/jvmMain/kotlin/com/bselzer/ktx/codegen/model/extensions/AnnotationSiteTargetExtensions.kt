package com.bselzer.ktx.codegen.model.annotation

import com.squareup.kotlinpoet.AnnotationSpec

fun AnnotationSiteTarget.toPoetSiteTarget(): AnnotationSpec.UseSiteTarget = when (this) {
    AnnotationSiteTarget.FILE -> AnnotationSpec.UseSiteTarget.FILE
    AnnotationSiteTarget.PROPERTY -> AnnotationSpec.UseSiteTarget.PROPERTY
    AnnotationSiteTarget.FIELD -> AnnotationSpec.UseSiteTarget.FIELD
    AnnotationSiteTarget.GET -> AnnotationSpec.UseSiteTarget.GET
    AnnotationSiteTarget.SET -> AnnotationSpec.UseSiteTarget.SET
    AnnotationSiteTarget.RECEIVER -> AnnotationSpec.UseSiteTarget.RECEIVER
    AnnotationSiteTarget.PARAM -> AnnotationSpec.UseSiteTarget.PARAM
    AnnotationSiteTarget.SETPARAM -> AnnotationSpec.UseSiteTarget.SETPARAM
    AnnotationSiteTarget.DELEGATE -> AnnotationSpec.UseSiteTarget.DELEGATE
}