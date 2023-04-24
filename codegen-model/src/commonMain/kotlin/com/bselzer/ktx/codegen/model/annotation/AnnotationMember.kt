package com.bselzer.ktx.codegen.model.annotation

data class AnnotationMember(
    val name: String,
    val value: String
) {
    override fun toString(): String = name
}