package com.bselzer.ktx.codegen.model.file.import

data class SimpleImport(
    override val name: String,
    override val alias: String? = null
) : Import