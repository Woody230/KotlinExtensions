package com.bselzer.ktx.compose.resource

import org.jetbrains.compose.resources.ExperimentalResourceApi

// TODO custom resource name https://youtrack.jetbrains.com/issue/CMP-4763/Compose-resources-Allow-custom-naming-of-Res-class-generated
object KtxResources {
    @OptIn(ExperimentalResourceApi::class)
    suspend fun readBytes(path: String) = Res.readBytes(path)

    @OptIn(ExperimentalResourceApi::class)
    fun getUri(path: String) = Res.getUri(path)

    val drawable = Res.drawable
    val string = Res.string
    val array = Res.array
    val plurals = Res.plurals
    val font = Res.font
}