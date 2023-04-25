package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object LongIdentifierResolver : IdentifierResolver() {
    override val value: ClassName = ClassName.LONG
    override val formats: Collection<String?> = setOf("int64")
    override val types: Collection<OpenApiSchemaType> = listOf(OpenApiSchemaType.INTEGER)
    override val identifier: ClassName = ClassName.LONG_IDENTIFIER
    override val defaultValue: CodeBlock = "0".toCodeBlock()
}