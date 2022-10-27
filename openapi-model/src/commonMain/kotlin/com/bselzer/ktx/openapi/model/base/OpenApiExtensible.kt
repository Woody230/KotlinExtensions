package com.bselzer.ktx.openapi.model.base

import com.bselzer.ktx.openapi.model.OpenApiExtensions

interface OpenApiExtensible {
    /**
     * The extensions properties are implemented as patterned fields that are always prefixed by "x-".
     */
    val extensions: OpenApiExtensions
}