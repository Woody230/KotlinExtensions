package com.bselzer.ktx.openapi.model

interface OpenApiExtensible {
    /**
     * The extensions properties are implemented as patterned fields that are always prefixed by "x-".
     */
    val extensions: OpenApiExtensions
}