package com.bselzer.ktx.codegen.model.extensions

import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.documentation.LiteralDocumentation

fun String.toDocumentation(): Documentation = LiteralDocumentation(this)