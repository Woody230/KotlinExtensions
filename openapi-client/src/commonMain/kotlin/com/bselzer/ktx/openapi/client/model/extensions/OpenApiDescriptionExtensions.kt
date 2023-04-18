package com.bselzer.ktx.openapi.client.model.extensions

import com.bselzer.ktx.codegen.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.OpenApiDescription

fun OpenApiDescription.toDocumentation() = toString().toDocumentation()